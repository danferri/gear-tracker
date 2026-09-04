# 🎸 Gear Tracker — Gerenciador de Instrumentos Musicais com IA

**API REST** desenvolvida com **Spring Boot** e **Spring AI** para gerenciar instrumentos musicais, customizações e manutenções por meio de **comandos de voz** processados por **inteligência artificial**.

---

## 📋 Sobre o Projeto

Este projeto foi desenvolvido como desafio do **Bootcamp Santander 2026 - Java Backend** na [DIO](https://www.dio.me/).

A aplicação recebe **arquivos de áudio** como entrada, transcreve a fala em texto usando o **OpenAI Whisper**, interpreta a intenção do usuário por meio de um **LLM (GPT-4o-mini)**, executa funções reais da aplicação via **Tool Calling** e retorna uma **resposta em áudio** usando **Text-to-Speech**.

### 🔄 Fluxo Principal

1. O usuário envia um **arquivo de áudio** (ex: *"Cadastra uma guitarra Fender Stratocaster de 5000 reais"*)
2. O **Whisper** transcreve o áudio em texto
3. O **LLM** interpreta a intenção e seleciona a **ferramenta correta**
4. A ferramenta **salva ou consulta** os dados no banco **MySQL**
5. A IA gera uma **resposta em texto**
6. O **Text-to-Speech** converte a resposta em **áudio (MP3)**

---

## ⭐ Melhorias Implementadas

O projeto base suportava apenas **duas ferramentas** de IA (salvar e listar transações). Esta versão adiciona as seguintes melhorias:

### ✅ 1. Validação de Domínio

A classe `InstrumentRecord` valida que:
- **Descrições** não podem ser vazias
- **Valores** devem ser maiores que zero

Isso **impede** que dados inválidos sejam salvos no banco.

### ✅ 2. Nova Ferramenta de IA: Cálculo de Total por Categoria

- Foi criado o `CalculateTotalByCategoryUseCase` e registrado como **ferramenta da IA**
- O assistente agora consegue responder perguntas como: *"Quanto eu já gastei com manutenção?"*
- Ele **calcula o valor exato** a partir do banco de dados

### ✅ 3. Tema Personalizado

O projeto foi personalizado de uma API genérica de orçamento financeiro para um **gerenciador de equipamentos musicais** com **6 categorias**:

| Categoria | Descrição |
|-----------|-----------|
| `GUITAR` | Guitarras |
| `BASS` | Baixos |
| `AMPLIFIER` | Amplificadores |
| `CUSTOMIZATION_DONE` | Customizações já realizadas |
| `CUSTOMIZATION_TODO` | Customizações pendentes |
| `MAINTENANCE` | Manutenções realizadas |

---

## 🛠️ Tecnologias Utilizadas

| Tecnologia | Finalidade |
|------------|-----------|
| **Java 21** | Linguagem principal |
| **Spring Boot 4** | Framework backend |
| **Spring AI** | Integração com IA (ChatClient, Tool Calling, Transcription, TTS) |
| **Spring Data JPA** | Persistência de dados |
| **MySQL** | Banco de dados (via Docker Compose) |
| **OpenAI API** | GPT-4o-mini, Whisper, Text-to-Speech |
| **Lombok** | Redução de código boilerplate |
| **Gradle** | Gerenciador de build e dependências |

---

## 🚀 Como Executar

### Pré-requisitos

- ☕ **Java 21+**
- 🐳 **Docker** (para o MySQL)
- 🔑 **Chave de API da OpenAI**

### Passos

**1.** Clone o repositório:

    git clone https://github.com/seu-usuario/gear-tracker.git

**2.** Configure a variável de ambiente com sua chave da OpenAI:

    export OPENAI_API_KEY="sua-chave-aqui"

**3.** Execute a aplicação (o Docker sobe automaticamente):

    ./gradlew bootRun

---

## 🧪 Como Testar

### Endpoints REST

| Método | Rota | Descrição |
|--------|------|-----------|
| **POST** | `/records` | Cadastra um registro via JSON |
| **GET** | `/records/{category}` | Lista registros por categoria |
| **POST** | `/records/ai` | 🎤 Envia áudio e recebe resposta em áudio |

### Exemplo de JSON (POST /records):

    {
      "description": "Fender Stratocaster American Professional",
      "category": "GUITAR",
      "amount": 500000
    }

> **💡 Nota:** O campo `amount` é em **centavos**. Então `500000` = **R$ 5.000,00**

---

## 📚 O Que Aprendi

- Como integrar **recursos de IA** em uma aplicação **Spring Boot** real
- Como usar o **Tool Calling** para criar uma ponte entre o raciocínio da IA e as regras de negócio do backend
- A importância de manter a **arquitetura limpa e organizada** mesmo quando se usa IA
- Como funciona o fluxo completo de **Speech-to-Text → LLM → Text-to-Speech**