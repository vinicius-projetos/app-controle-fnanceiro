# -*- coding: utf-8 -*-
from reportlab.lib.pagesizes import A4
from reportlab.lib.units import cm, mm
from reportlab.lib.colors import Color, HexColor, white
from reportlab.lib.styles import getSampleStyleSheet, ParagraphStyle
from reportlab.lib.enums import TA_JUSTIFY, TA_CENTER, TA_LEFT
from reportlab.platypus import (
    SimpleDocTemplate, Paragraph, Spacer, Table, TableStyle,
    PageBreak, KeepTogether, ListFlowable, ListItem, HRFlowable, Image,
)
from pathlib import Path
from PIL import Image as PILImage

PASTA_PRINTS = Path(r"c:\Users\vinic\AndroidStudioProjects\Recipes\docs\prints")
from reportlab.pdfbase import pdfmetrics
from reportlab.pdfbase.ttfonts import TTFont

VERDE = HexColor("#00875A")
VERDE_ESCURO = HexColor("#0B3D2E")
FUNDO = HexColor("#F6F8F7")
CINZA = HexColor("#5F6C72")
TEXTO = HexColor("#10221B")
BORDA = HexColor("#C5D5CC")

LARGURA, ALTURA = A4
MARGEM = 2 * cm


def cabecalho_rodape(canvas, doc):
    canvas.saveState()
    canvas.setFillColor(VERDE)
    canvas.rect(0, ALTURA - 12, LARGURA, 12, fill=1, stroke=0)
    canvas.rect(0, 0, LARGURA, 18, fill=1, stroke=0)
    canvas.setFillColor(white)
    canvas.setFont("Helvetica", 8)
    canvas.drawString(MARGEM, 6, "EduFin  ·  Documentação do MVP  ·  FIAP")
    canvas.drawRightString(LARGURA - MARGEM, 6, f"Página {doc.page}")
    canvas.restoreState()


def capa_fundo(canvas, doc):
    canvas.saveState()
    canvas.setFillColor(VERDE_ESCURO)
    canvas.rect(0, 0, LARGURA, ALTURA, fill=1, stroke=0)
    canvas.setFillColor(VERDE)
    canvas.rect(0, ALTURA - 2.2 * cm, LARGURA, 2.2 * cm, fill=1, stroke=0)
    canvas.rect(0, 0, LARGURA, 2.2 * cm, fill=1, stroke=0)
    canvas.setFillColor(white)
    canvas.setFont("Helvetica", 9)
    canvas.drawCentredString(LARGURA / 2, 1 * cm, "Atividade avaliativa  ·  Aplicativo ESG  ·  Sem backend próprio")
    canvas.restoreState()


def estilos():
    s = getSampleStyleSheet()
    s.add(ParagraphStyle(
        "CapaTitulo", fontName="Helvetica-Bold", fontSize=28, leading=34,
        textColor=white, alignment=TA_CENTER, spaceAfter=8,
    ))
    s.add(ParagraphStyle(
        "CapaSub", fontName="Helvetica", fontSize=13, leading=18,
        textColor=HexColor("#D7EDE4"), alignment=TA_CENTER, spaceAfter=6,
    ))
    s.add(ParagraphStyle(
        "CapaMeta", fontName="Helvetica", fontSize=11, leading=16,
        textColor=white, alignment=TA_CENTER,
    ))
    s.add(ParagraphStyle(
        "H1", fontName="Helvetica-Bold", fontSize=14, leading=18,
        textColor=VERDE_ESCURO, spaceBefore=14, spaceAfter=8,
    ))
    s.add(ParagraphStyle(
        "H2", fontName="Helvetica-Bold", fontSize=12, leading=16,
        textColor=VERDE, spaceBefore=10, spaceAfter=6,
    ))
    s.add(ParagraphStyle(
        "Corpo", fontName="Helvetica", fontSize=10.5, leading=15,
        textColor=TEXTO, alignment=TA_JUSTIFY, spaceAfter=8,
    ))
    s.add(ParagraphStyle(
        "Comentario", fontName="Helvetica-Oblique", fontSize=10, leading=14,
        textColor=CINZA, alignment=TA_JUSTIFY, spaceBefore=4, spaceAfter=8,
        leftIndent=8, rightIndent=8, borderPadding=6,
    ))
    s.add(ParagraphStyle(
        "Url", fontName="Helvetica-Bold", fontSize=11, leading=15,
        textColor=VERDE, alignment=TA_CENTER, spaceBefore=6, spaceAfter=6,
    ))
    s.add(ParagraphStyle(
        "LegendaPrint", fontName="Helvetica", fontSize=9, leading=12,
        textColor=CINZA, alignment=TA_CENTER, spaceBefore=4, spaceAfter=10,
    ))
    s.add(ParagraphStyle(
        "Celula", fontName="Helvetica", fontSize=9.5, leading=13, textColor=TEXTO,
    ))
    s.add(ParagraphStyle(
        "CelulaTitulo", fontName="Helvetica-Bold", fontSize=9.5, leading=13, textColor=white,
    ))
    return s


def figura(rotulo, arquivo=None):
    if arquivo:
        caminho = PASTA_PRINTS / arquivo
        if caminho.exists():
            with PILImage.open(caminho) as pil:
                px_w, px_h = pil.size
            img = Image(str(caminho))
            img.drawWidth = 8.4 * cm
            img.drawHeight = px_h * (8.4 * cm / px_w)
            max_h = 13.5 * cm
            if img.drawHeight > max_h:
                img.drawWidth = img.drawWidth * (max_h / img.drawHeight)
                img.drawHeight = max_h
            img.hAlign = "CENTER"
            return [img, Paragraph(rotulo, estilos()["LegendaPrint"])]

    aviso = Paragraph(
        f"<b>INSERIR PRINT AQUI</b><br/>{rotulo}<br/><font size='8'>"
        "Ainda falta esta captura (seção de Metas no Painel).</font>",
        ParagraphStyle(
            "PrintBox", fontName="Helvetica", fontSize=10, leading=14,
            alignment=TA_CENTER, textColor=CINZA,
        ),
    )
    tabela = Table([[aviso]], colWidths=[16.5 * cm], rowHeights=[7.2 * cm])
    tabela.setStyle(TableStyle([
        ("BACKGROUND", (0, 0), (-1, -1), FUNDO),
        ("BOX", (0, 0), (-1, -1), 1, BORDA),
        ("VALIGN", (0, 0), (-1, -1), "MIDDLE"),
        ("ALIGN", (0, 0), (-1, -1), "CENTER"),
        ("LEFTPADDING", (0, 0), (-1, -1), 12),
        ("RIGHTPADDING", (0, 0), (-1, -1), 12),
    ]))
    return [tabela, Paragraph(rotulo, estilos()["LegendaPrint"])]


def secao_tela(st, numero, titulo, descricao, comentarios, rotulo_print, arquivo=None):
    blocos = [
        Paragraph(f"{numero}. {titulo}", st["H2"]),
        Paragraph(descricao, st["Corpo"]),
        Paragraph("<b>Comentários sobre as funcionalidades.</b> " + comentarios, st["Comentario"]),
    ]
    blocos.extend(figura(rotulo_print, arquivo))
    return blocos


def gerar(caminho):
    st = estilos()
    doc = SimpleDocTemplate(
        caminho,
        pagesize=A4,
        leftMargin=MARGEM,
        rightMargin=MARGEM,
        topMargin=2.2 * cm,
        bottomMargin=1.8 * cm,
        title="EduFin — Documentação do MVP",
        author="Equipe EduFin",
        subject="Documentação da atividade avaliativa ESG",
    )

    historia = []

    # ---- CAPA ----
    historia.append(Spacer(1, 5.5 * cm))
    historia.append(Paragraph("EduFin", st["CapaTitulo"]))
    historia.append(Paragraph("Educação financeira na palma da mão", st["CapaSub"]))
    historia.append(Spacer(1, 0.6 * cm))
    historia.append(HRFlowable(width="40%", thickness=1.5, color=VERDE, spaceBefore=4, spaceAfter=12, hAlign="CENTER"))
    historia.append(Paragraph("Documentação do MVP Android", st["CapaMeta"]))
    historia.append(Paragraph("Atividade avaliativa — aplicativo com temática ESG", st["CapaMeta"]))
    historia.append(Spacer(1, 2.2 * cm))
    historia.append(Paragraph(
        "Nome(s): ________________________________<br/><br/>"
        "RM(s): __________________________________<br/><br/>"
        "Turma: __________________________________<br/><br/>"
        "Data: ___________________________________",
        st["CapaMeta"],
    ))
    historia.append(PageBreak())

    # ---- OBJETIVO ----
    historia.append(Paragraph("1. Objetivo do aplicativo", st["H1"]))
    historia.append(Paragraph(
        "O <b>EduFin</b> (Educação Financeira) é um aplicativo Android voltado a quem quer "
        "entender o próprio dinheiro sem precisar de conta bancária, cadastro ou backend próprio. "
        "A proposta é transformar taxas reais da economia brasileira — SELIC, CDI e IPCA — em "
        "decisões práticas: quanto sobra no mês, quanto uma dívida custa de verdade e quanto "
        "um aporte pequeno pode render com o tempo.",
        st["Corpo"],
    ))
    historia.append(Paragraph(
        "O MVP demonstra um fluxo completo de uso: o usuário vê a saúde financeira no painel, "
        "cadastra renda e gastos, compara o custo de uma dívida com juros compostos e simula "
        "investimentos em 1, 5 e 10 anos. As metas aparecem no painel para reforçar o hábito "
        "de guardar com objetivo. Tudo isso roda no aparelho, com persistência em memória nesta "
        "versão, e se conecta a um serviço público já existente para atualizar as taxas.",
        st["Corpo"],
    ))
    historia.append(Paragraph(
        "Em resumo, o objetivo é <b>democratizar o acesso à informação financeira</b>, "
        "traduzindo números de mercado em linguagem simples, para reduzir endividamento "
        "inconsciente e estimular poupança e investimento consciente.",
        st["Corpo"],
    ))

    # ---- TECNOLOGIAS ----
    historia.append(Paragraph("2. Tecnologias utilizadas", st["H1"]))
    historia.append(Paragraph(
        "O aplicativo foi desenvolvido de forma nativa para Android, seguindo o padrão do "
        "curso (Kotlin, Jetpack Compose e Material 3), sem servidor próprio.",
        st["Corpo"],
    ))

    dados = [
        [Paragraph("Camada", st["CelulaTitulo"]), Paragraph("Escolha e justificativa", st["CelulaTitulo"])],
        [Paragraph("Linguagem", st["Celula"]), Paragraph("Kotlin", st["Celula"])],
        [Paragraph("Interface", st["Celula"]), Paragraph("Jetpack Compose e Material Design 3, com tema claro e escuro e fonte Poppins", st["Celula"])],
        [Paragraph("Navegação", st["Celula"]), Paragraph("Navigation Compose, com rotas tipadas (sealed class) e barra inferior compartilhada", st["Celula"])],
        [Paragraph("Arquitetura", st["Celula"]), Paragraph("Pacotes screens, components, model, repository, network e util, no padrão br.com.fiap.edufin", st["Celula"])],
        [Paragraph("API", st["Celula"]), Paragraph("Retrofit, OkHttp e Gson para consumir a BrasilAPI em HTTPS", st["Celula"])],
        [Paragraph("Internacionalização", st["Celula"]), Paragraph("strings.xml em inglês (padrão), português do Brasil e espanhol", st["Celula"])],
        [Paragraph("Plataforma", st["Celula"]), Paragraph("Android nativo — minSdk 28, sem backend customizado", st["Celula"])],
    ]
    tabela = Table(dados, colWidths=[4.2 * cm, 12.3 * cm])
    tabela.setStyle(TableStyle([
        ("BACKGROUND", (0, 0), (-1, 0), VERDE),
        ("BACKGROUND", (0, 1), (-1, 1), FUNDO),
        ("BACKGROUND", (0, 3), (-1, 3), FUNDO),
        ("BACKGROUND", (0, 5), (-1, 5), FUNDO),
        ("BACKGROUND", (0, 7), (-1, 7), FUNDO),
        ("VALIGN", (0, 0), (-1, -1), "TOP"),
        ("LEFTPADDING", (0, 0), (-1, -1), 8),
        ("RIGHTPADDING", (0, 0), (-1, -1), 8),
        ("TOPPADDING", (0, 0), (-1, -1), 6),
        ("BOTTOMPADDING", (0, 0), (-1, -1), 6),
        ("GRID", (0, 0), (-1, -1), 0.4, BORDA),
    ]))
    historia.append(tabela)
    historia.append(Spacer(1, 0.35 * cm))
    historia.append(Paragraph(
        "Não há banco de dados remoto nem API própria. Orçamento, dívidas e simulações são "
        "calculados no dispositivo. A única chamada de rede é a consulta às taxas oficiais.",
        st["Corpo"],
    ))

    # ---- ESG ----
    historia.append(Paragraph("3. Relação com ESG", st["H1"]))
    historia.append(Paragraph(
        "O EduFin se posiciona no pilar <b>Social (S)</b> da agenda ESG. Educação financeira "
        "é um fator de inclusão: quem não entende juros, inflação e orçamento fica mais "
        "exposto a rotativo de cartão, crediário caro e decisões impulsivas. O app atua "
        "como ferramenta de alfabetização financeira, sem vender produto bancário.",
        st["Corpo"],
    ))
    historia.append(Paragraph(
        "<b>Social.</b> O aplicativo reduz assimetria de informação. As taxas SELIC, CDI e IPCA "
        "deixam de ser jargão e passam a aparecer no dia a dia do usuário, ligadas a quanto "
        "sobra no mês, quanto uma dívida cresce e quanto R$ 50 por mês podem render. Isso "
        "favorece autonomia, especialmente de quem está começando a vida financeira.",
        st["Corpo"],
    ))
    historia.append(Paragraph(
        "<b>Governança (indireta).</b> O app não substitui instituição financeira, não coleta "
        "senha de banco e não exige cadastro. Consome um serviço público e documentado "
        "(BrasilAPI), o que reforça transparência sobre a origem dos dados. A poupança, que "
        "não vem nesse endpoint, é estimada pela regra conhecida do Banco Central, deixando "
        "claro o critério de cálculo.",
        st["Corpo"],
    ))
    historia.append(Paragraph(
        "<b>Ambiental.</b> Não é o foco do MVP. O recorte social foi escolhido de propósito: "
        "o impacto imediato está em comportamento financeiro, não em pegada de carbono.",
        st["Corpo"],
    ))

    # ---- TELAS ----
    historia.append(Paragraph("4. Telas do aplicativo", st["H1"]))
    historia.append(Paragraph(
        "O MVP possui quatro telas navegáveis pela barra inferior (Painel, Orçamento, Dívidas "
        "e Investir) e uma quinta área funcional de <b>Metas financeiras</b>, apresentada no "
        "Painel com progresso de cada objetivo. A aba Metas já aparece na navegação e fica "
        "reservada para a próxima evolução. Abaixo, cada tela é descrita, comentada e "
        "acompanhada de espaço para o print obrigatório.",
        st["Corpo"],
    ))

    historia.extend(secao_tela(
        st, "4.1", "Painel inicial",
        "É a tela de abertura. Mostra a saudação, um card de saúde financeira (No azul, "
        "Atenção ou No vermelho) com a sobra do mês, renda e gastos, uma faixa horizontal "
        "com as taxas do dia (SELIC, CDI, IPCA e Poupança) e a lista de metas com barra "
        "de progresso. A barra inferior e o botão flutuante (+) levam ao orçamento.",
        "A saúde financeira é derivada da folga sobre a renda: 20% ou mais de sobra é "
        "considerado “no azul”; sobra positiva abaixo disso gera atenção; gasto maior que "
        "renda fica no vermelho. As taxas vêm da BrasilAPI quando há internet; sem rede, "
        "o app avisa e mantém os últimos valores conhecidos. O painel e o orçamento "
        "compartilham o mesmo repositório, então qualquer alteração de gasto ou renda "
        "atualiza o card imediatamente ao voltar para esta tela.",
        "Figura 1 — Painel inicial",
        "01-painel.jpg",
    ))

    historia.extend(secao_tela(
        st, "4.2", "Orçamento",
        "Tela interativa de cadastro. O usuário informa a renda mensal, adiciona gastos "
        "com nome e valor e remove itens pela lixeira. Um resumo mostra o total de gastos, "
        "a sobra do mês e o percentual da renda comprometida, com barra colorida segundo "
        "o nível de saúde financeira.",
        "O botão “Adicionar gasto” só habilita com nome preenchido e valor maior que zero, "
        "evitando lançamento vazio. O campo aceita vírgula ou ponto decimal, porque o "
        "teclado numérico muda conforme o aparelho. Não há backend: a lista fica em memória "
        "durante a sessão, o que é suficiente para demonstrar o fluxo no MVP e pode ser "
        "trocado por DataStore ou Room sem alterar o visual.",
        "Figura 2 — Orçamento",
        "02-orcamento.jpg",
    ))

    historia.extend(secao_tela(
        st, "4.3", "Calculadora de dívidas",
        "O usuário informa valor da dívida, taxa de juros mensal e prazo em meses. Ao "
        "calcular, o app mostra valor inicial, total de juros, valor final e o peso dos "
        "juros sobre o montante. A fórmula usa juros compostos: valor × (1 + taxa)^meses.",
        "A intenção pedagógica é tornar visível o custo escondido do rotativo, do crediário "
        "e de empréstimos. O botão só libera com dados válidos (valor e prazo positivos, "
        "taxa numérica). Esta tela não depende da API: a taxa é digitada para o usuário "
        "comparar cenários reais que ele vê na fatura ou no contrato.",
        "Figura 3 — Calculadora de dívidas",
        "03-dividas.jpg",
    ))

    historia.extend(secao_tela(
        st, "4.4", "Simulador de investimentos",
        "Simula aportes mensais (atalhos de R$ 50, R$ 100 e R$ 200, além de valor livre) "
        "em SELIC, CDI, IPCA ou Poupança. A projeção cobre 1, 5 e 10 anos, separando o "
        "que a pessoa guarda do que rende, com barra proporcional. A taxa usada é a da "
        "BrasilAPI (ou a reserva, offline).",
        "O cálculo é de juros compostos com depósitos no fim de cada mês, convertendo a "
        "taxa anual para equivalente mensal. Há aviso de que rentabilidade passada não "
        "garante resultado futuro. O objetivo é mostrar o efeito do tempo: R$ 50 por mês "
        "parece pouco, mas em dez anos a diferença entre “só guardar” e “render” fica "
        "evidente — alinhado à educação financeira do pilar Social.",
        "Figura 4 — Simulador de investimentos",
        "04-investir.png",
    ))

    historia.extend(secao_tela(
        st, "4.5", "Metas financeiras",
        "As metas aparecem no Painel: reserva de emergência, curso técnico e notebook para "
        "estudar, cada uma com valor guardado, valor alvo, percentual e barra de progresso. "
        "Essa seção cumpre o papel da quinta tela do MVP: o usuário vê objetivos concretos "
        "ligados à sobra do orçamento. A aba Metas na barra inferior já sinaliza o destino "
        "da evolução (criação e edição em tela própria).",
        "O progresso é valorGuardado / valorAlvo, limitado entre 0% e 100%. Os dados desta "
        "versão são de demonstração (repositório local), para ilustrar o hábito de poupar "
        "com finalidade — reserva, estudo e ferramenta de trabalho — em vez de poupança "
        "abstrata. Para o print, role o Painel até “Suas metas” e capture essa parte.",
        "Figura 5 — Metas financeiras (seção no Painel)",
    ))

    # ---- API ----
    historia.append(Paragraph("5. Serviço / API utilizado", st["H1"]))
    historia.append(Paragraph(
        "Em atendimento ao enunciado, o aplicativo consome um serviço já existente, em "
        "HTTPS, sem backend próprio. A integração é com a <b>BrasilAPI</b>, endpoint de taxas.",
        st["Corpo"],
    ))
    historia.append(Paragraph("https://brasilapi.com.br/api/taxas/v1", st["Url"]))
    historia.append(Paragraph(
        "A chamada é GET, sem autenticação. A resposta é uma lista JSON com nome e valor "
        "percentual ao ano, por exemplo SELIC, CDI e IPCA. O Retrofit mapeia esse JSON "
        "para o modelo do app. A caderneta de poupança não faz parte desse endpoint; o "
        "aplicativo estima o rendimento anual pela regra usual do Banco Central: 0,5% ao "
        "mês quando a SELIC está acima de 8,5% ao ano, ou 70% da SELIC abaixo desse patamar.",
        st["Corpo"],
    ))
    historia.append(Paragraph(
        "A permissão INTERNET está declarada no AndroidManifest. Se a requisição falhar, "
        "o repositório mantém a lista local e a interface informa que não há conexão, "
        "para o app continuar utilizável offline nas demais funções (orçamento, dívida e "
        "simulação com a última taxa conhecida).",
        st["Corpo"],
    ))

    historia.append(Paragraph("6. Considerações finais", st["H1"]))
    historia.append(Paragraph(
        "O EduFin entrega um MVP navegável, com tema claro e escuro, três idiomas, "
        "componentização Compose e consumo real de API pública. O recorte ESG é Social: "
        "educação financeira acessível. Os prints das telas, inseridos nos espaços desta "
        "documentação, completam a evidência visual exigida na atividade.",
        st["Corpo"],
    ))

    def primeira_pagina(canvas, doc):
        capa_fundo(canvas, doc)

    def demais(canvas, doc):
        cabecalho_rodape(canvas, doc)

    doc.build(historia, onFirstPage=primeira_pagina, onLaterPages=demais)


if __name__ == "__main__":
    saida = r"c:\Users\vinic\AndroidStudioProjects\Recipes\docs\EduFin-Documentacao.pdf"
    gerar(saida)
    print(saida)
