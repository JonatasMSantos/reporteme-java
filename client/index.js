const express = require('express');
const SockJS = require('sockjs-client');
const Stomp = require('stompjs');

const app = express();

// Porta em que o servidor express será executado
const PORT = 80;

// Endpoint do WebSocket Spring Boot
const WS_URL = 'http://localhost:8080/ws';

// Função para conectar ao WebSocket e ouvir mensagens
function connectWebSocket() {
    // Conectar ao servidor SockJS
    const socket = new SockJS(WS_URL);
    const stompClient = Stomp.over(socket);

    // Desabilitar logs de depuração no Stomp.js
    stompClient.debug = null;

    // Conectar ao servidor STOMP
    stompClient.connect({}, function (frame) {
        console.log('Conectado: ' + frame);

        // Assinar o tópico /propostas
        stompClient.subscribe('/proposal', function (response) {
            // Converter a mensagem recebida para JSON
            const message = JSON.parse(response.body);

            // Imprimir a mensagem no console
            console.log('Mensagem recebida:', message);
        });
    });


    // Callback de erro
    stompClient.onWebSocketError = function (error) {
        console.error('Erro na conexão WebSocket:', error);
    };

    // Callback de desconexão
    stompClient.onWebSocketClose = function () {
        console.log('Conexão WebSocket fechada.');
    };
}

// Iniciar o servidor Express na porta 80
app.listen(PORT, () => {
    console.log(`Servidor Express rodando na porta ${PORT}`);
    // Conectar ao WebSocket quando o servidor iniciar
    connectWebSocket();
});
