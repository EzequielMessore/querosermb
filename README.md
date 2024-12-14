# Exchanges App

## Visão Geral

O Exchanges App é um aplicativo Android baseado em Kotlin.
O projeto é estruturado usando uma arquitetura modular para garantir escalabilidade, manutenibilidade e facilidade de teste.

## Estrutura do Projeto

O projeto é dividido em vários módulos, cada um responsável por um aspecto específico do aplicativo:

- **app**: O módulo principal do aplicativo contendo o ponto de entrada e a configuração geral.
- **build-logic**: Contém plugins Gradle personalizados e configurações de lógica de build.
- **core**: Contém funcionalidades e utilitários principais compartilhados em todo o aplicativo.
  - **core:view-model**: Classes e utilitários relacionados ao ViewModel.
  - **core:navigation**: Classes e utilitários relacionados à navegação.
  - **core:testing**: Utilitários de teste e classes base.
- **data**: Gerencia fontes de dados, incluindo manipulação de dados locais e remotos.
  - **data:local**: Fontes de dados locais, como bancos de dados.
  - **data:remote**: Fontes de dados remotos, como APIs.
- **domain**: Contém a lógica de negócios e casos de uso.
- **presentation**: Gerencia os componentes e temas da interface do usuário.
  - **presentation:designsystem**: Componentes de UI reutilizáveis e elementos do sistema de design.

## Requisitos Básicos

Para rodar o aplicativo, você precisará dos seguintes requisitos:

- **JDK 17**
- **Android Studio** (versão recomendada: Android Studio Ladybug | 2024.2.1 Patch 3)
- **Gradle 8.9** ou superior
- **API Key da CoinAPI** (obtenha uma em [CoinAPI Documentation](https://docs.coinapi.io/?shell#list-all-exchanges-get))

## Como Rodar o Aplicativo

1. Clone o repositório:
   ```sh
   git clone git@github.com:EzequielMessore/querosermb.git
   ```
2. Abra o projeto no Android Studio.
3. Sincronize o projeto com os arquivos Gradle.
4. Conecte um dispositivo físico ou inicie um emulador.
5. Compile e execute o aplicativo.

## Como Configurar a API Key

1. Abra o arquivo `local.properties` localizado no diretório raiz do projeto.
2. Adicione sua API key ao arquivo:
   ```ini
   API_KEY=your_api_key_here
   ```
3. O aplicativo usará automaticamente essa API key durante a execução.

## Como Rodar os Testes

Para rodar os testes unitários e de UI, siga os passos abaixo:

1. Abra o projeto no Android Studio.
2. Para rodar todos os testes, use o seguinte comando no terminal:
   ```sh
   ./gradlew testDebugUnitTest
   ```
3. Para rodar testes de UI (instrumentation tests), use o seguinte comando:
   ```sh
   ./gradlew connectedAndroidTest
   ```
