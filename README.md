## Contextualização

Este projeto foi desenvolvido para o desafio Global Solutions da FIAP, focado na Economia Azul. O desafio busca soluções tecnológicas que promovam a sustentabilidade dos oceanos, abordando problemas como poluição marinha, destruição de habitats e mudanças climáticas.

# O projeto

A Wave 🌊 é uma aplicação que permite aos usuários em regiões litorâneas ou margens de rios reportar o lixo observado na água ou nas proximidades. Os usuários podem descrever o tipo de lixo encontrado e o grau de gravidade. Essas informações são processadas internamente e exibidas em um mapa interativo. Assim, outras pessoas podem coletar e remover o lixo do mapa, ajudando a manter o controle das regiões afetadas. Os dados coletados são enviados para ONGs locais, permitindo uma ação mais eficaz e direcionada.

Para tornar nossa solução ainda mais envolvente, incorporamos a gamificação. Usuários podem subir de nível e receber conquistas ao longo da jornada de denúncias e coletas, incentivando a participação contínua nessa causa vital.

[Assista à apresentação do nosso projeto](https://www.youtube.com/watch?v=3d7CwsVDy3U)

## Funcionalidades

- **Mapeamento de Lixo**: Visualização de lixos reportados em um mapa interativo.
- **Relatórios de Poluição**: Usuários podem reportar a presença de lixo em áreas costeiras.
- **ChatBot de Suporte**: Assistente virtual para ajudar os usuários com informações e suporte.
- **Sistema de Autenticação**: Autenticação de usuários para identificação dos reportes e utilização do sistema de níveis.
- **Integração com Python**: Uso de Python para processamento de dados e visualização de mapas.

## Tecnologias Utilizadas

- **Frontend**: HTML, CSS, JavaScript
- **Backend**: Python (Flask)
- **Database**: Appwrite
- **ChatBot**: TypeBot, Google Gemini, n8n

## Como Executar

O mapa presente no nosso frontend pode ser acessado [aqui](https://bluefuture-trashmap.onrender.com) e para fazer requisições POST, basta adicionar _/report_ no final da URL.
Caso queira rodar o mapa localmente, siga as instruções abaixo:

1. Clone o repositório:

   ```bash
   git clone https://github.com/seu-usuario/blue-future.git
   cd blue-future
   ```

2. Instale as dependências do Python:

   ```bash
   cd backend/python
   pip install -r requirements.txt
   ```

3. Inicie o servidor:

   ```bash
   python app.py
   ```

4. Acesse a aplicação em seu navegador:

   ```
   http://127.0.0.1:5000
   ```

5. Modifique as chamadas de API no Frontend:
   ```
   Substitua "https://bluefuture-trashmap.onrender.com" por "http://127.0.0.1:5000", e "https://bluefuture-trashmap.onrender.com/report" por "http://127.0.0.1:5000"
   ```

## Contribuidores

- **Arthur Vieira Mariano**
- **Guilherme Henrique Maggiorini**
- **Ian Rossato Braga**
