# T1-DSM
### Trabalho desenvolvido para a disciplina de Desenvolvimento de Software Mobile 2022/01 utilizando Java e Firebase 30.3.1, desenvolvido integralmente no Android Studio 2021.2.1 pelos alunos: *Julio Cesar dos Santos Oliveira Filho - RA: 779800* e *Vanderlei de Brito Junior - RA: 636932*.
***
## Descrição do Projeto
<p>O projeto, chamado até o momento de Gamers Moments, tem como intuito conectar jogadores que desejam jogar juntos! Para isso, você, como um usuário, cria uma publicação com qual jogo deseja jogar, a quantidade de vagas em seu time e o nível de habilidade mínima que deseja, assim, outros jogadores verão sua publicação e irão pedir permissão para entrar no seu grupo.</p>
<p>Com isso, uma notificação ao criador da publicação é enviada, e caso o convite seja aceito, vocês serão conectados por um chat em tempo real para organizar detalhes da jogatina.</p>

***
***
## Requisitos Atendidos
1. [x] R1. Identidade Visual
    * Foi escolhida uma coloração padrão entre preto e diversos tons de roxo.
    * Há um degradê no background seguindo essa identidade visual.
    * Links e imagens-vetores também segue tons de lilás.
2. [x] R2. Quantidade de Telas
    * O aplicativo possui Activites de Login, Signup, Recuperação de Senha, Perfil, Home (com todas as publicações), além de alguns fragments, como o menu *hamburguer* na lateral, com acesso rápido as páginas
3. [x] R3. Rede
    * O aplicativo utiliza um backend real, implementado com Firebase, com um CRUD quase completo e acesso total ao banco de dados
4. [x] R4. Armazenamento Local
    * Nosso aplicativo armazena localmente as credenciais do usuário, assim, quando o aplicativo é fechado, sua sessão não é encerrada.
    * Dados da localização e idioma, também são salvos localmente e pré-carregados na inicialização do aplicativo.
5. [x] R5. Internacionalização
    * O aplicativo tem dois idiomas: Inglês e Português. O qual pode ser selecionado na tela de Login.
    * Todas as informações estão nos dois idiomas, incluido, as Toasts (que no caso são Snacks) com mensagens de erro.
***
***
## Requisitos
1. Android Studio 2021.2.1. Instalado e configurado.
2. Firebase 30.3.1. Inteiramente online, necessário apenas possui uma conta gmail configurada. Seguir o passo a passo de configuração do site [Firebase Documentação](https://firebase.google.com/docs/android/setup?hl=pt-br)
3. <s>Um computador minimamente descente com alguns gigabytes de memória RAM!</s>
4. Necessário uma conexão com a internet, visto que o banco de dados é inteiramente online. Apenas preferencias locais são salvas sem conexão com a internet.
***
***
## Como Executar
1. Faça o download do repositório.
```
git clone git@github.com:Julio-Cesar07/T1_DSM.git
```
2. Acesse a pasta com o Android Studio.
3. Configure o emulador ou conecte o celular no qual pretende executar a aplicação.
4. Aguarde a compilação e pronto.
