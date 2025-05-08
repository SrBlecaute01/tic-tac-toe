# Jogo da Velha - Banco de Dados I

## 📜 Sobre o Projeto
Este projeto é um **jogo da velha** desenvolvido como parte da **avaliação do curso de Banco de Dados I** . 
O objetivo principal é demonstrar a integração de uma aplicação Java com um banco de dados relacional, 
utilizando consultas SQL para persistência e recuperação de dados.

O jogo permite que dois jogadores disputem uma partida clássica de jogo da velha, com funcionalidades 
como rastreamento de jogadas, gerenciamento de turnos e detecção automática de vencedores ou empates.

<div style="display: flex; justify-content: center;">
  <video src="https://github.com/SrBlecaute01/tic-tac-toe/tree/assets/preview.mp4" controls width="600"></video>
</div>
---

## 🛠️ Tecnologias Utilizadas
- **Linguagem de Programação**: Java (com recursos `--enable-preview`)
- **Banco de Dados**: SQLite
- **Ferramenta de Build**: Maven
- **Frameworks/Bibliotecas**:
   - [Lombok](https://projectlombok.org/) para redução de código boilerplate
   - [JLine](https://jline.github.io/) para interação no terminal
   - [Apache Logback](https://logback.qos.ch/) para logging
   - [MyBatis](https://mybatis.org/) para execução do script SQL
   - [HikariCP](https://github.com/brettwooldridge/HikariCP) para gerenciamento de conexões
   - [SQL Provider](https://github.com/Jaoow/sql-provider) para abstração de consultas SQL
   - [AsciiTable](https://github.com/vdmeer/asciitable) para exibição de tabelas no terminal
   - [SLF4J](http://www.slf4j.org/) como API de logging

---

## 📂 Estrutura do Projeto
```
src/
├── main/
│   ├── java/
│   │   ├── dev/
│   │   │   ├── arnaldo/
│   │   │   │   ├── tic/
│   │   │   │   │   ├── tac/
│   │   │   │   │   │   ├── toe/
│   │   │   │   │   │   │   ├── domain/       # Modelos de domínio
│   │   │   │   │   │   │   ├── service/      # Lógica de negócio
│   │   │   │   │   │   │   ├── repository/   # Operações de banco de dados 
│   │   │   │   │   │   │   ├── util/         # Classes utilitárias 
│   │   │   │   │   │   │   ├── view/         # Interfaces baseadas em terminal
│   ├── resources/                            # Arquivos de configuração 
```

---

## 🚀 Como Instalar e Executar

### Pré-requisitos
1. **Java 17 ou superior** (com recursos de pré-visualização habilitados)
2. **Maven** para gerenciamento de dependências
3. **SQLite** como Banco de Dados Relacional
4. **Prompt de Comando do Windows** (para executar o `start.bat`)

### Passos
1. Clone o repositório:
   ```bash
   git clone https://github.com/SrBlecaute01/tic-tac-toe.git
   cd tic-tac-toe
   ```
   
2. Compile o projeto:
   ```bash
   mvn clean install
   ```

3. Execute a aplicação:
    - Utilize o script `start.bat` fornecido:
      ```bash
      start.bat
      ```
    - Ou execute diretamente o arquivo JAR:
      ```bash
      java --enable-preview --enable-native-access=ALL-UNNAMED -Dfile.encoding=UTF-8 -jar target/TicTacToe-1.0.0.jar
      ```

---