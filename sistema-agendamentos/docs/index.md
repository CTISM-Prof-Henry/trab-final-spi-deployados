# Software de agendamento de Salas de Aula do Colégio Politécnico

## Para que serve?

Este aplicativo foi desenvolvido para auxiliar à comunidade acadêmica e docente a usufruir da estrutura disponível no Colégio Politécnico. Com o objetivo de permitir o agendamento de salas e laboratórios, através da realização das atividades de classe e extra-classe.

## Funcionalidades

#### **Autenticação de login:**  
Acesso livre ao site, porém para realizar agendamentos é necessário a autenticação através do CPF e senha de usuário.

#### **Dashboard de agendamento:**  
O dashboard apresenta um calendário, onde o usuário irá selecionar o bloco desejado e a sala. Posteriormente, será exibido no calendário os agendamentos para a semana daquela sala. Clicando no botão de "Agendar", o usuário conseguirá realizar um agendamento ou será redirecionado para a página de login (caso não esteja logado).

#### **Controle de agendamentos:**  
Uma página onde é possível visualizar os agendamentos realizados pelo usuário e seu status.

#### **Controle de salas – reservado a administradores:**  
Uma página onde usuários com controle de administrador conseguem cadastrar, alterar e ativar/desativar salas.\


## Requisitos para funcionamento

Configuração da máquina em que o código foi desenvolvido:

| Configuração        | Valor                    |
|---------------------|--------------------------|
| Sistema operacional | Windows                  |
| Necessita rede?     | Sim                      |
| Ferramenta          | Intellij IDEA            |


## Tecnologias utilizadas

#### **Backend:**  
Java 21, Spring Boot, Flyway e Hibernate

#### **Frontend:**  
Angular 12 e Node

#### **Banco de Dados:**  
PostgreSQL

## Como funciona

* Instalar dependências no seu computador:

    * NodeJS (versão recomendada 16.13.0):
    
    ` https://nodejs.org/en/download`
    
    * Angular (dentro do prompt de comando, verificando se o `node` está funcionando e o gerenciador de pacotes `npn`, digitar o seguinte comando):
    
    `npm install -g @angular/cli@12.2.17`
        
    * Java JDK 21 ou superior
    
    * PostgreSQL 16 ou superior
    
    **Lembrando que, se já houver uma versão do angular instalado em sua máquina, será preciso desinstalar.**
    
    
## Instruções de Uso

* Dê um fork através do github no projeto usando os seguintes endereços

    * Backend: `https://github.com/CTISM-Prof-Henry/trab-final-spi-deployados/`

    * Frontend: `https://github.com/CTISM-Prof-Henry/trab-final-spi-deployados/frontend`
    

## Como rodar

   1. De o git clone no repositório de backend: `git clone https://github.com/CTISM-Prof-Henry/trab-final-spi-deployados/`.
   2. Criar o banco de dados "sistema-agendamentos".
   3. Troque a senha do seu banco de dados no arquivo application.proprieties para a sua senha.
   4. Rode o backend. Ele estará rodando na porta 8080, você poderá confirmar no link http://localhost:8080.
   5. De o git clone no repositório de frontend: `git clone https://github.com/CTISM-Prof-Henry/trab-final-spi-deployados/frontend`
   6. Instale as dependências do Angular. Use o comando `npm install`
   7. Rode o frontend, com o comando `ng serve` (caso sua versão global do Angular seja a 12.2.17).
   8. Caso sua versão não seja a global use o comando para rodar o angular com a versão do angular local (na pasta onde foi instalada as dependências): `npx ng serve`.
   9. O frontend estará rodando na porta 4200, você poderá confirmar no link http://localhost:4200.



