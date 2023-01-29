package com.krillsson.sysapi.mdns

import com.krillsson.sysapi.config.SysAPIConfiguration
import com.krillsson.sysapi.util.EnvironmentUtils
import com.krillsson.sysapi.util.logger
import io.dropwizard.jetty.HttpConnectorFactory
import io.dropwizard.jetty.HttpsConnectorFactory
import io.dropwizard.lifecycle.Managed
import io.dropwizard.server.DefaultServerFactory
import io.dropwizard.server.SimpleServerFactory
import java.net.InetAddress
import java.net.InetSocketAddress
import java.net.Socket
import javax.jmdns.JmDNS
import javax.jmdns.ServiceInfo


class Mdns(val configuration: SysAPIConfiguration) : Managed {

    private val logger by logger()

    lateinit var jmdns: JmDNS

    override fun start() {
        val address = findLocalIp()
        logger.info("Address: $address")
        jmdns = JmDNS.create()
        connectorFactories()
            .mapNotNull {
                when (it) {
                    is HttpsConnectorFactory -> "https" to it.port
                    is HttpConnectorFactory -> "http" to it.port
                    else -> null
                }
            }.forEach { (scheme, port) ->
                val serviceType = "_$scheme._tcp.local"
                val serviceName = "SysAPI-$scheme @ ${EnvironmentUtils.hostName}"
                logger.info("Registering mDNS: $serviceType with name: $serviceName at port $port")
                val serviceInfo = ServiceInfo.create(serviceType, serviceName, port, "GraphQL at /graphql")
                jmdns.registerService(serviceInfo)
            }

    }

    private fun connectorFactories() =
        ((configuration.serverFactory as? DefaultServerFactory)?.applicationConnectors.orEmpty() + (configuration.serverFactory as? SimpleServerFactory)?.connector)

    override fun stop() {
        jmdns.unregisterAllServices()
    }

    private fun findLocalIp(): InetAddress {
        val socket = Socket()
        return try {
            socket.connect(InetSocketAddress("google.com", 80))
            socket.localAddress
        } catch (exception: Exception) {
            val localHost = InetAddress.getLocalHost()
            logger.error("Exception while resolving local IP, resorting to $localHost", exception)
            localHost
        }
    }

}