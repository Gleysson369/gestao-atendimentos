<h1 align="center">Olá 👋, eu sou Gleysson Flavio</h1>
<h3 align="center">Desenvolvedor do Sistema de Gestão de Atendimentos</h3>

- 🔭 Atualmente estou trabalhando no **Sistema de Gestão de Atendimentos**
- 🌱 Atualmente estou aprendendo **Spring Boot, Java**
- 💬 Pergunte-me sobre **Spring Boot**
- 📫 Como me contatar: **GLEYSSON_FLAVIO@HOTMAIL.COM**

<h3 align="left">Conecte-se comigo:</h3>
<p align="left">
    </p>

<h3 align="left">Linguagens e Ferramentas:</h3>
<p align="left">
    <a href="https://www.w3schools.com/css/" target="_blank" rel="noreferrer"> <img src="https://raw.githubusercontent.com/devicons/devicon/master/icons/css3/css3-original-wordmark.svg" alt="css3" width="40" height="40"/> </a>
    <a href="https://www.figma.com/" target="_blank" rel="noreferrer"> <img src="https://www.vectorlogo.zone/logos/figma/figma-icon.svg" alt="figma" width="40" height="40"/> </a>
    <a href="https://git-scm.com/" target="_blank" rel="noreferrer"> <img src="https://www.vectorlogo.zone/logos/git-scm/git-scm-icon.svg" alt="git" width="40" height="40"/> </a>
    <a href="https://www.w3.org/html/" target="_blank" rel="noreferrer"> <img src="https://raw.githubusercontent.com/devicons/devicon/master/icons/html5/html5-original-wordmark.svg" alt="html5" width="40" height="40"/> </a>
    <a href="https://www.java.com" target="_blank" rel="noreferrer"> <img src="https://raw.githubusercontent.com/devicons/devicon/master/icons/java/java-original.svg" alt="java" width="40" height="40"/> </a>
    <a href="https://www.postgresql.org" target="_blank" rel="noreferrer"> <img src="https://raw.githubusercontent.com/devicons/devicon/master/icons/postgresql/postgresql-original-wordmark.svg" alt="postgresql" width="40" height="40"/> </a>
    <a href="https://spring.io/" target="_blank" rel="noreferrer"> <img src="https://www.vectorlogo.zone/logos/springio/springio-icon.svg" alt="spring" width="40" height="40"/> </a>
</p>

---

# Sistema de Gestão de Atendimentos

Este projeto é um sistema de gestão de atendimentos desenvolvido com Spring Boot, focado em oferecer uma solução intuitiva e eficiente para o controle de interações com clientes. Ele permite o cadastro de usuários, o registro detalhado de atendimentos e a geração de relatórios para análise de dados.

## Tecnologias Utilizadas

* **Spring Boot**: Framework principal para desenvolvimento da aplicação Java.
* **Spring Security**: Para controle de autenticação e autorização de usuários.
* **Spring Data JPA**: Para persistência de dados no banco de dados.
* **Thymeleaf**: Motor de template para as páginas web.
* **PostgreSQL**: Banco de dados relacional.
* **Maven**: Ferramenta de automação de construção de projetos.
* **HTML, CSS, JavaScript**: Para a construção das interfaces de usuário.

## Configuração do Ambiente

Para rodar este projeto em sua máquina, siga os passos abaixo:

### Pré-requisitos

* **Java Development Kit (JDK) 17 ou superior**.
* **Maven 3.x**.
* **PostgreSQL**: Certifique-se de ter uma instância do PostgreSQL rodando.

### Configuração do Banco de Dados

1.  Crie um banco de dados PostgreSQL com o nome `gestao_db`.
2.  No arquivo `src/main/resources/application.properties`, configure as credenciais do seu banco de dados:

    ```properties
    spring.datasource.url=jdbc:postgresql://localhost:5432/gestao_db
    spring.datasource.username=postgres
    spring.datasource.password=sua_senha_do_postgres # Altere para sua senha
    spring.datasource.driver-class-name=org.postgresql.Driver
    spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
    spring.jpa.hibernate.ddl-auto=update
    spring.jpa.show-sql=true
    spring.thymeleaf.cache=false
    ```

    * O `spring.jpa.hibernate.ddl-auto=update` fará com que o Hibernate crie e atualize automaticamente as tabelas do banco de dados com base em suas entidades Java.

### Executando a Aplicação

1.  Navegue até a pasta raiz do projeto no seu terminal.
2.  Compile o projeto usando Maven:

    ```bash
    mvn clean install
    ```
3.  Execute a aplicação Spring Boot:

    ```bash
    mvn spring-boot:run
    ```

    A aplicação estará disponível em `http://localhost:8080`.

## Visão Geral das Funcionalidades

O aplicativo de Gestão de Atendimentos é composto por seis telas principais, seguindo um design com três tons de azul e branco. As cores são configuradas em um arquivo CSS principal (`_variables.css` ou `main.css` conforme sua estrutura).

### 1. Tela de Login

* **Acesso:** `http://localhost:8080/login` ou `http://localhost:8080/`.
* **Funcionalidade:** Permite que usuários acessem o sistema.
* **Campos:** ID de Login e Senha.
* **Opções:** "Esqueceu a Senha?" (redireciona para a redefinição de senha), "Lembrar-me" (checkbox), Botão "Login", Botão "Voltar".
* **Fluxo de Autenticação:** Ao informar as credenciais e clicar em "Login", o sistema deve validá-las. Em caso de sucesso, o usuário será redirecionado para a "Tela Principal". Em caso de credenciais inválidas, uma mensagem de erro será exibida.
* **Regras de Acesso:** Usuários comuns devem ter um cadastro prévio, feito na Tela de Cadastro de Usuário pelo usuário ADMIN. Eles devem usar o ID de Login e a senha cadastrados para acessar.

### 2. Tela de Redefinição de Senha

* **Acesso:** Exclusivamente via o link "Esqueceu a Senha?" na Tela de Login.
* **Funcionalidade:** Permite que o usuário redefina sua senha.
* **Campos:** ID de Login e Nova Senha.
* **Ação:** Após informar os dados e clicar em "Salvar", a senha é atualizada e o usuário é redirecionado para a Tela de Login.

### 3. Tela Principal (Home)

* **Acesso:** Após login bem-sucedido.
* **Funcionalidade:** Dashboard central com opções de navegação e informações de BI.
* **Menu:** Três botões com efeito de mudança de cor ao passar o mouse:
    * **Cadastro de Usuário**: Acesso à tela de gerenciamento de usuários.
    * **Cadastro de Atendimentos**: Acesso à tela de registro de atendimentos.
    * **Relatório de Atendimentos**: Acesso à tela de relatórios e análises.
* **Dashboard de BI:** Exibe a quantidade de atendimentos (mensal e diário), Top 10 Clientes atendidos e Top 10 Empresas atendidas.
* **Botão "Sair":** Desloga da aplicação e retorna para a Tela de Login.

### 4. Tela de Cadastro de Usuário

* **Acesso:** Através do botão "Cadastro de Usuário" na Tela Principal.
* **Funcionalidade:** Gerenciamento de usuários do sistema (cadastro, alteração e exclusão).
* **Campos:** ID de Login, Nome Completo, Senha, Departamento (dropdown), Cargo (dropdown).
* **Departamentos:** Vendas, Logística, Técnico, Financeiro, Fiscal Emissão, Fiscal Apuração, Fiscal Entradas, Compras, Outros.
* **Cargos:** Atendente, Analista, Supervisor, Gestor.
* **Ações:** Botões "Salvar", "Deletar", "Alterar Cadastro". Mensagens de sucesso são exibidas após cada operação.
* **Permissões:** Somente usuários com a autoridade **ADMIN** podem alterar ou deletar dados de outros usuários.
* **Navegação:** Botão "Voltar ao Menu Principal" para retornar à Tela Principal.

### 5. Tela de Cadastro de Atendimentos

* **Acesso:** Através do botão "Cadastro de Atendimentos" na Tela Principal.
* **Funcionalidade:** Registro e gerenciamento de atendimentos.
* **Campos:** Empresa do Cliente, Nome do Cliente, Data e Hora de Atendimento, Motivo do Contato, Solução Passada, Número da CRS, Telefone, AnyDesk, Canal de Atendimento (dropdown).
* **Canais de Atendimento:** Telefone, Mkon WhatsApp, E-mail.
* **Ações:** Botões "Salvar", "Alterar", "Deletar".
* **Navegação:** Botão "Voltar ao Menu Principal" para retornar à Tela Principal.

### 6. Tela de Relatório de Atendimentos

* **Acesso:** Através do botão "Relatório de Atendimentos" na Tela Principal.
* **Funcionalidade:** Visualização e exportação de dados de atendimentos com filtros.
* **Filtros:** Nome do Cliente, Empresa do Cliente, Período de Atendimento (data inicial e final). O relatório é atualizado dinamicamente.
* **Dados Exibidos:** Empresa do Cliente, Nome do Cliente, Data e Hora de Atendimento, Motivo do Contato, Solução Passada, Número da CRS, Telefone, AnyDesk, Canal de Atendimento.
* **Ordenação:** Capacidade de ordenar por Nome do Cliente ou Data de Atendimento.
* **Exportação:** Botões "Imprimir", "Gerar PDF", "Exportar para Excel".
* **Navegação:** Botão "Voltar ao Menu Principal" para retornar à Tela Principal.

---

**Observações para o Desenvolvedor:**

* A classe `SecurityConfig.java` configura as regras de segurança, permitindo acesso público às páginas de login e aos arquivos estáticos (`css`, `js`, `img`), enquanto todas as outras requisições exigem autenticação. Ela também configura o formulário de login padrão e o processo de logout.
* O `BCryptPasswordEncoder` é utilizado para criptografar as senhas dos usuários, garantindo maior segurança.
* A classe `CadastroUsuarioController.java` possui anotações `@PreAuthorize("hasAuthority('ADMIN')")` para garantir que apenas usuários com a função 'ADMIN' possam realizar operações de edição e exclusão de usuários.
* A estrutura de pastas (`controller`, `model`, `repository`, `service`) está bem definida, seguindo as melhores práticas do Spring Boot.

---
