# Order Management System (Microservices)

Este projeto é uma implementação de um **Sistema de Gerenciamento de Pedidos** utilizado como fonte de estudos para um arquitetura baseada em **Microservices** e **Orientada a Eventos (Event-Driven Architecture)**. O sistema simula um fluxo completo de gerenciamento de pedidos, desde a criação até o pagamento e a verificação de estoque, utilizando eventos para comunicação entre os microservices. A arquitetura foi projetada para ser escalável, resiliente e eficiente.

## Arquitetura

O sistema é dividido em 4 microservices principais:

1. **Order Service** (Serviço de Pedidos)  
2. **Inventory Service** (Serviço de Estoque)  
3. **Payment Service** (Serviço de Pagamento)  
4. **Notification Service** (Serviço de Notificação)

A comunicação entre os serviços é realizada por eventos assíncronos, utilizando o Kafka.

## Funcionalidades

- **Criar Pedido**: O cliente pode criar um pedido, que será processado pelo sistema.
- **Verificar Status do Pedido**: O cliente pode consultar o status do seu pedido.
- **Cancelar Pedido**: O cliente pode cancelar um pedido antes que o pagamento seja confirmado.
- **Estoque**: O sistema verifica a disponibilidade dos produtos e reserva o estoque.
- **Pagamento**: O sistema processa pagamentos de forma assíncrona.
- **Notificações**: O cliente recebe notificações por e-mail ou SMS quando um pedido é criado, pago ou cancelado.

## Endpoints da API

### **1. Criar Pedido**
`[POST] /orders`
- **Descrição**: Cria um novo pedido.
- **Entrada**:
  ```json
  {
    "customerId": 123,
    "itens": [
      { 
        "id": 1, 
        "quantity": 2 
      },
      { 
        "id": 2, 
        "quantity": 1 
      }
    ],
    "delivery": {
      "address": {
        "street": "Rua Exemplo",
        "number": "10A",
        "city": "São Paulo",
        "state": "SP",
        "country": "Brazil",
        "zipcode": 00000000
      }
    }
  }
- **Saida**:
  ```json
  {
    "id": 456,
    "status": "pending"
  }

### **2. Consultar Status do Pedido**
`[GET] /orders/{id}`
- **Descrição**: Obtém o status de um pedido.
- **Saida**:
  ```json
  {
    "id": 456,
    "status": "in_progress",
    "payment": {
      "status": "approved"
    },
    "inventory": {
      "status": "reserved"
    },
    "itens": [
      { 
        "id": 1, 
        "quantity": 2 
      },
      { 
        "id": 2, 
        "quantity": 1 
      }
    ]
  }

### **3. Cancelar Pedido**
`[POST] /orders/{id}/cancel`
- **Descrição**: Cancela um pedido, caso o pagamento ainda não tenha sido confirmado.
- **Saida**:
  ```json
  {
    "id": 456,
    "status": "canceled"
  }


### **4. Listar Pedidos**
`[GET] /orders`
- **Descrição**: Lista todos os pedidos com filtros por status (ex: pending, in_progress, paid, completed, canceled).
- **Saida**:
  ```json
  [
    {
      "id": 456,
      "status": "pending",
      "customerId": 123
    },
    {
      "id": 457,
      "status": "completed",
      "customerId": 124
    }
  ]
## Arquitetura de Eventos

A comunicação entre os microservices é baseada em eventos assíncronos. Os eventos principais são:

1. **Pedido Criado**: O serviço de pedidos emite um evento após a criação de um novo pedido.
2. **Estoque Reservado**: O serviço de estoque emite um evento quando o estoque é reservado com sucesso.
3. **Estoque Insuficiente**: O serviço de estoque emite um evento quando não há estoque suficiente para o pedido.
4. **Pagamento Confirmado**: O serviço de pagamento emite um evento quando o pagamento é confirmado.
5. **Pagamento Falhou**: O serviço de pagamento emite um evento quando o pagamento falha.
6. **Pedido Cancelado**: O serviço de pedidos emite um evento quando um pedido é cancelado.

### Fluxo de Eventos:

1. O **Order Service** cria um pedido e emite o evento "Pedido Criado".
2. O **Inventory Service** verifica a disponibilidade do estoque e emite "Estoque Reservado" ou "Estoque Insuficiente".
3. O **Payment Service** processa o pagamento e emite "Pagamento Confirmado" ou "Pagamento Falhou".
4. O **Notification Service** envia notificações para o cliente conforme os eventos (Pedido Criado, Pagamento Confirmado, etc.).