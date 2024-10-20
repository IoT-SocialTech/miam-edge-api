package com.miam.edgeApi.configuration;

import com.ibm.msg.client.jms.JmsConnectionFactory;
import com.ibm.msg.client.jms.JmsFactoryFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.connection.JmsTransactionManager;
import org.springframework.jms.connection.UserCredentialsConnectionFactoryAdapter;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class JMSConfig
{
    private static final Logger log = LogManager.getLogger(JMSConfig.class);

    @Value("${spring.ibm.mq.host}")
    private String host;

    @Value("${spring.ibm.mq.port}")
    private Integer port;

    @Value("${spring.ibm.mq.queue-manager}")
    private String queueManager;

    @Value("${spring.ibm.mq.channel}")
    private String channel;

    @Value("${spring.ibm.mq.username:#{null}}")
    private String username;

    @Value("${spring.ibm.mq.password:#{null}}")
    private String password;

    @Value("${spring.ibm.mq.receive-timeout}")
    private long receiveTimeout;

    @Bean
    public JmsTemplate queueJmsTemplate() throws Exception {
        JmsTemplate jmsTemplate = new JmsTemplate(cachingConnectionFactory());
        jmsTemplate.setReceiveTimeout(this.receiveTimeout);
        return jmsTemplate;
    }

    @Bean
    public JmsConnectionFactory queueJmsConnectionFactory() throws Exception {
        JmsFactoryFactory ff = JmsFactoryFactory.getInstance("com.ibm.msg.client.wmq");
        JmsConnectionFactory cf = ff.createConnectionFactory();

        cf.setStringProperty("XMSC_WMQ_HOST_NAME", this.host);
        cf.setIntProperty("XMSC_WMQ_PORT", this.port.intValue());
        cf.setStringProperty("XMSC_WMQ_CHANNEL", this.channel);
        cf.setIntProperty("XMSC_WMQ_CONNECTION_MODE", 1);
        cf.setStringProperty("XMSC_WMQ_QUEUE_MANAGER", this.queueManager);
        cf.setStringProperty("XMSC_USERID", this.username);
        cf.setStringProperty("XMSC_PASSWORD", this.password);
        cf.setBooleanProperty("XMSC_USER_AUTHENTICATION_MQCSP", true);

        return cf;
    }

    @Bean
    UserCredentialsConnectionFactoryAdapter userCredentialsConnectionFactoryAdapter() throws Exception {
        UserCredentialsConnectionFactoryAdapter userCredentialsConnectionFactoryAdapter = new UserCredentialsConnectionFactoryAdapter();
        userCredentialsConnectionFactoryAdapter.setUsername(this.username);
        userCredentialsConnectionFactoryAdapter.setPassword(this.password);
        userCredentialsConnectionFactoryAdapter.setTargetConnectionFactory(queueJmsConnectionFactory());
        return userCredentialsConnectionFactoryAdapter;
    }

    @Bean
    @Primary
    public CachingConnectionFactory cachingConnectionFactory() throws Exception {
        CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory();
        if (this.username != null) {
            cachingConnectionFactory.setTargetConnectionFactory(userCredentialsConnectionFactoryAdapter());
        } else {
            cachingConnectionFactory.setTargetConnectionFactory(queueJmsConnectionFactory());
        }
        cachingConnectionFactory.setSessionCacheSize(500);
        cachingConnectionFactory.setReconnectOnException(true);
        return cachingConnectionFactory;
    }

    @Bean
    @Primary
    public PlatformTransactionManager jmsTransactionManager() throws Exception {
        JmsTransactionManager jmsTransactionManager = new JmsTransactionManager();
        jmsTransactionManager.setConnectionFactory(cachingConnectionFactory());
        return jmsTransactionManager;
    }

    @Bean
    public JmsListenerContainerFactory<?> queueListenerContainerFactory() throws Exception {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(cachingConnectionFactory());
        factory.setPubSubDomain(Boolean.FALSE);
        factory.setSessionTransacted(true);
        factory.setTransactionManager(jmsTransactionManager());
        return factory;
    }

    @Bean
    public JmsListenerContainerFactory<?> queueListenerContainerFactoryWithDelay() throws Exception {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(cachingConnectionFactory());
        factory.setReceiveTimeout(10000L);
        factory.setPubSubDomain(Boolean.FALSE);
        factory.setSessionTransacted(true);
        factory.setTransactionManager(jmsTransactionManager());
        return factory;
    }
}
