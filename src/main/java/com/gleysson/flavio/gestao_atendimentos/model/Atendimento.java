package com.gleysson.flavio.gestao_atendimentos.model;

import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

@Entity
public class Atendimento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String empresaCliente;
    private String nomeCliente;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime dataHoraAtendimento;

    @Column(length = 1000)
    private String motivoContato;

    @Column(length = 1000)
    private String solucaoPassada;

    private String numeroCRS;
    private String telefone;
    private String anydesk;

    private Boolean atendimentoTransferencia;
    private String protocoloAtendimento;

    @Enumerated(EnumType.STRING)
    private CanalAtendimento canalAtendimento;

    @Enumerated(EnumType.STRING)
    private TipoDeOcorrencia tipoDeOcorrencia;

    @ManyToOne
    @JoinColumn(name = "usuario_atendente_id")
    private Usuario usuarioAtendente;

    public Atendimento(){
    }

    
    public enum CanalAtendimento {
        TELEFONE("Telefone"),
        WHATSAPP("WhatsApp"), 
        MKON_WHATSAPP("Mkon WhatsApp"), 
        PRESENCIAL("Presencial"),
        OUTRO("Outro");

        private final String displayValue;

        CanalAtendimento(String displayValue) {
            this.displayValue = displayValue;
        }

        public String getDisplayValue() {
            return displayValue;
        }
    }

    public enum TipoDeOcorrencia {
        ACESSO_ONLINE_DE_VENDAS("Acesso Online de Vendas"),
        ACESSOS_VIOLATION("Acessos Violation"),
        ACERTO_DE_ESTOQUE_FISICO("Acerto de Estoque Físico"),
        ACOMPANHAMENTO_DE_INVENTARIO("Acompanhamento de Inventário"),
        ACOMPANHAMENTO_DE_VIRADA("Acompanhamento de Virada"),
        ACOMPANHAMENTO_POS_ATUALIZACAO("Acompanhamento Pós Atualização"),
        ADM_WEB("Adm Web"),
        AJUSTAR_NOTA_NFE_NFC_E_ECF_NFS_E("Ajustar Nota NFE / NFC-E / ECF / NFS-E"),
        AJUSTAR_O_TIPO_DE_OCORRENCIA("Ajustar o Tipo de Ocorrência"),
        AJUSTE_IMPORTADOR_DE_DADOS_SISTEMA_CONCORRENTE("Ajuste Importador de Dados Sistema Concorrente"),
        AJUSTE_DE_CADASTROS("Ajuste de Cadastros"),
        AJUSTE_DE_ESTOQUE_FISCAL_E_FISICO("Ajuste de Estoque Fiscal e Físico"),
        AJUSTE_DE_ETIQUETAS("Ajuste de Etiquetas"),
        AJUSTE_DE_HORARIO_DE_IMPRESSORA_FISCAL("Ajuste de Horário de Impressora Fiscal"),
        AJUSTE_DE_LAYOUTS_DE_TELAS_DO_SISTEMA("Ajuste de Layouts de Telas do Sistema"),
        AJUSTE_DE_LOGOMARCAS("Ajuste de Logomarcas"),
        AJUSTE_DE_PLANO_FINANCEIRO("Ajuste de Plano Financeiro"),
        AJUSTE_DE_REGISTRO_DO_ORACLE_CHARSET("Ajuste de Registro do Oracle (Charset)"),
        AJUSTE_DE_TRIBUTACAO("Ajuste de Tributação"),
        AJUSTES_REF_AO_MODULO_DE_SERVICO_MECOL("Ajustes Ref ao Modulo de Servico MecoL"),
        ALTERACAO_CADASTRO_CLIENTE("Alteração Cadastro Cliente"),
        ALTERACAO_DE_AUT("Alteração de Aut"),
        ALTERACAO_DE_IP_DE_SERVIDOR("Alteração de IP de Servidor"),
        ALTERACAO_DE_SENHA("Alteração de Senha"),
        ALTERAR_CFOP_DADOS_DE_NOTA_FISCAL("Alterar CFOP/Dados de Nota Fiscal"),
        ALTERAR_DADOS_DA_EMPRESA("Alterar Dados da Empresa"),
        ANALISAR_AJUSTAR_PROCESSOS_DE_FRETES_NA_VENDA("Analisar/Ajustar Processos de Fretes na Venda"),
        ANALISE_DE_ACOMPANHAMENTO_DE_PEDIDOS("Analise de Acompanhamento de Pedidos"),
        ANALISE_DE_BALANCO("Análise de Balanço"),
        ANALISE_DE_NOVA_LEGISLACAO("Analise de Nova Legislação"),
        ANALISE_DE_XML("Analise de XML"),
        ANALISE_EM_BASE_DE_TESTE("Analise em Base de Teste"),
        ANALISE_PROCESSOS_DE_ENTREGAS("Analise Processos de Entregas"),
        ANALISE_AJUSTES_DE_PROCESSOS_AUTOMATICOS("Analise/Ajustes de Processos Automaticos"),
        ANALISTA_DE_SUPORTE_DESATIVAR("Analista de Suporte Desativar"),
        APURACAO_DE_CREDITO_ICMS_ST_DF("Apuração de Credito ICMS ST-DF"),
        APURACAO_DE_IMPOSTOS("Apuração de Impostos"),
        APURACAO_DE_REGIMES_ESPECIAIS("Apuração de Regimes Especiais"),
        APURACAO_IR_CSLL("Apuração IR/CSLL"),
        ATENDIMENTO("Atendimento"),
        ATENDIMENTO_AGENDADO("Atendimento Agendado"),
        ATENDIMENTO_FINALIZADO_PELO_CLIENTE_LOGMEIN("Atendimento Finalizado pelo Cliente (LOGMEIN)"),
        ATENDIMENTO_VIA_TELEFONE_SOMENTE_OCORRENCIA("Atendimento Via Telefone (Somente Ocorrência)"),
        ATIVAR_MODULOS_DO_ADM("Ativar Módulos do ADM"),
        ATIVAR_PARAMETROS("Ativar Parametros"),
        ATUALIZACAO_DE_VERSAO("Atualização de Versão"),
        ATUALIZAR_INFORMACOES_DO_CRM("Atualizar Informações do CRM"),
        ATUALIZAR_VERSAO_DO_JAVA("Atualizar Versão do Java"),
        AUDITORIA_CONTABIL("Auditoria Contábil"),
        AUXILIO_A_DEMAIS_DISPOSITIVOS("Auxilio a Demais Dispositivos"),
        BAIXA_AUTOMATICA_DE_CARTAO("Baixa Automática de Cartão"),
        BAIXA_AUTOMATICA_DE_CARTOES("Baixa Automática de Cartões"),
        BALANCETE("Balancete"),
        BALANCO_PATRIMONIAL("Balanço Patrimonial"),
        BLOQ_AUT_PELO_PARAM_GERAL_TIPO_BLOQUEIO_COMPRA("Bloq. Aut. Pelo Param. Geral Tipo Bloqueio Compra"),
        BLOQUEIO_DE_SISTEMA_PEDENCIA_FINANCEIRA_SANTRI("Bloqueio de Sistema Pedência Financeira Santri"),
        BLOQUEIOS_DE_ESTOQUE_EM_ABERTO_PARA_O_FORNECEDOR("Bloqueios de Estoque em Aberto Para o Fornecedor"),
        BONIFICACOES_PEND_COM_A_PREVISAO_DE_ENT_VENC("Bonificações Pend com a Previsão de Ent. Venc"),
        CADASTRO_DE_CONTAS_A_PAGAR_RECEBER("Cadastro de Contas a Pagar/Receber"),
        CADASTRO_DE_NOVO_CNPJ("Cadastro de Novo CNPJ"),
        CADASTRO_DE_PLANO_FINANCEIRO("Cadastro de Plano Financeiro"),
        CADASTRO_ERRADO_DE_IMPRESSORA_FISCAL("Cadastro Errado de Impressora Fiscal"),
        CADASTROS_EST_CIDADES_BAIRROS("Cadastros (Est. / Cidades / Bairros)"),
        CANCELAMENTO_DE_NF_E_NFC_E_E_OUTROS_DOCS_FISCAIS("Cancelamento de NF-E/NFC-E e Outros Docs Fiscais"),
        CANCELAMENTO_EXTEMPORANEO("Cancelamento Extemporâneo"),
        CAPTA("Capta"),
        CARTA_DE_CORRECAO("Carta de Correção"),
        CENTRAL_DE_MENSAGENS("Central de Mensagens"),
        COMPORTAMENTO_DO_FUNCIONARIO("Comportamento do Funcionário"),
        COMPRA_ACIMA_DO_VALOR_PARAMETRIZADO("Compra Acima do Valor Parametrizado"),
        CADASTRAR_ALIQUOTA_DE_ICMS_NA_ECF("Cadastrar Aliquota de ICMS na ECF"),
        CADASTRO_DE_CARTAO("Cadastro de Cartão"),
        CADASTRO_DE_CARTOES("Cadastro de Cartões"),
        CONCILIACAO_BANCARIA("Conciliação Bancária"),
        CONEXAO_PRESA_NO_BANCO_DE_DADOS("Conexão Presa no Banco de Dados"),
        CONF_DE_IMPRESSORA_LASER_MATRICIAL_ETIQUETA("Conf. de Impressora Laser / Matricial / Etiqueta"),
        CONF_DE_PRODUTOS("Conf. de Produtos"),
        CONF_NF_E_NFC_E_SATE_E_OUTROS_DOCS_FISCAIS("Conf. NF-E / NFC-E / SATE e Outros Docs Fiscais"),
        CONFIGURACAO_ALTERACAO_DE_SMS("Configuração / Alteração de SMS"),
        CONFIGURACAO_MANUTENCAO_DE_COMPARTILHAMENTO("Configuração / Manutenção de Compartilhamento"),
        CONFIGURACAO_MANUTENCAO_DE_COMPUTADORES("Configuração / Manutenção de Computadores"),
        CONFIGURACAO_MANUTENCAO_DE_DOMINIO("Configuração / Manutenção de Dominio"),
        CONFIGURACAO_MANUTENCAO_DE_IMPRESSORAS("Configuração / Manutenção de Impressoras"),
        CONFIGURACAO_MANUTENCAO_DE_MODEM("Configuração / Manutenção de Modem"),
        CONFIGURACAO_MANUTENCAO_DE_POCKETS_LEITORES("Configuração / Manutenção de Pocket's / Leitores"),
        CONFIGURACAO_MANUTENCAO_DE_REDE("Configuração / Manutenção de Rede"),
        CONFIGURACAO_MANUTENCAO_DE_SERVIDORES("Configuração / Manutenção de Servidores"),
        CONFIGURACAO_MANUTENCAO_DE_TERMINAL_SERVER("Configuração / Manutenção de Terminal Server"),
        CONFIGURACAO_MANUTENCAO_DE_THINCLIENT("Configuração / Manutenção de Thinclient"),
        CONFIGURACAO_MANUTENCAO_DE_VPN("Configuração / Manutenção de VPN"),
        CONFIGURACAO_ADM_SERVICO("Configuracao Adm Servico"),
        CONFIGURACAO_CAT52("Configuracao CAT52"),
        CONFIGURACAO_CONTVER_3_0("Configuracao Contver 3.0"),
        CONFIGURACAO_DE_BANCO_DE_DADOS("Configuracao de Banco de Dados"),
        CONFIGURACAO_DE_COBRANCA_BANCARIA("Configuração de Cobrança Bancária"),
        CONFIGURACAO_DE_CUPOM_PROMOCIONAL("Configuração de Cupom Promocional"),
        CONFIGURACAO_DE_EMAIL("Configuracao de Email"),
        CONFIGURACAO_DE_IMPRESSORA_FISCAL("Configuração de Impressora Fiscal"),
        CONFIGURACAO_DE_O_S("Configuração de O.S."),
        CONFIGURACAO_DE_PROFISSIONAIS("Configuracao de Profissionais"),
        CONFIGURACAO_DE_TERMINAL_DE_QUALIFICACAO("Configuracao de Terminal de Qualificacao"),
        CONFIGURACAO_DE_WINDOWS_SERVER("Configuração de Windows Server"),
        CONFIGURACAO_DO_BUSCA_PRECO("Configuracao do Busca Preco"),
        CONFIGURACAO_DO_HORARIO_DO_SERVIDOR("Configuração do Horario do Servidor"),
        CONFIGURACAO_DO_TS("Configuração do TS"),
        CONFIGURACAO_E_INSTALACAO_ADMASSINATURASDIGITAIS("Configuração e Instalação Admassinaturasdigitais"),
        CONFIGURACAO_E_INSTALACAO_ADMLOGISTA("Configuração e Instalação Admlogista"),
        CONFIGURACAO_E_INSTALACAO_ADMREQUISICOES("Configuração e Instalação Admrequisicoes"),
        CONFIGURACAO_E_INSTALACAO_ADMROTINASSERVER("Configuração e Instalação Admrotinasserver"),
        CONFIGURACAO_E_INSTALACAO_APK_MANIFESTO("Configuração e Instalação Apk Manifesto"),
        CONFIGURACAO_E_INSTALACAO_MALA_DIRETA("Configuração e Instalação Mala Direta"),
        CONFIGURACAO_E_INSTALACAO_NFESERVICE("Configuração e Instalação Nfeservice"),
        CONFIGURACAO_E_INSTALACAO_WHATSAPP("Configuração e Instalação Whatsapp"),
        CONFIGURACAO_LINUX("Configuração Linux"),
        CONFIGURACAO_MAC("Configuração Mac"),
        CONFIGURACAO_REINF("Configuração Reinf"),
        CONFIGURACAO_SERVIDOR_DE_FIREWALL("Configuração Servidor de Firewall"),
        CONFIGURACAO_ZABBIX("Configuração Zabbix"),
        CONFIGURACOES_BANCO_WEB("Configuracoes Banco Web"),
        CONFIGURACOES_DE_COMISSAO("Configuracoes de Comissao"),
        CONFIGURACOES_DE_CONDICOES_DE_PAGAMENTOS("Configurações de Condições de Pagamentos"),
        CONSULTA_FISCAL("Consulta Fiscal"),
        CONSULTA_NO_FONTE_DO_ADM("Consulta no Fonte do Adm"),
        CONSULTORIA("Consultoria"),
        CONSULTORIA_DE_ESTOQUE_FISICO_E_FISCAL("Consultoria de Estoque Fisico e Fiscal"),
        CONTABILIDADE("Contabilidade"),
        CONTAGEM_ACOMPANHADA("Contagem Acompanhada"),
        CONTAS_CONTABEIS("Contas Contábeis"),
        CONTROLE_DE_ATENDIMENTO("Controle de Atendimento"),
        CONTROLE_DE_LOTE("Controle de Lote"),
        CONTROLE_DE_QUALIDADE("Controle de Qualidade"),
        CONVOCACAO_ATIVA("Convocação Ativa"),
        CORRECAO_DE_ALIQUOTA("Correção de Aliquota"),
        CORRECAO_DE_ESTOQUE("Correção de Estoque"),
        COTACAO_DE_EQUIPAMENTOS("Cotação de Equipamentos"),
        CREDENCIAMENTO_SISTEMA_ADM_NA_SEFAZ_DO_ESTADO("Credenciamento Sistema Adm na Sefaz do Estado"),
        CRIAR_BASE_DE_CONSULTA("Criar Base de Consulta"),
        CRIAR_BASE_DE_TESTE("Criar Base de Teste"),
        CUSTOS_DE_PRODUTOS("Custos de Produtos"),
        DDA("DDA"),
        DEFINIR_REGRA_DE_RESERVA("Definir Regra de Reserva"),
        DESCANCELAMENTO_NFE("Descancelamento NFE"),
        DESENVOLVIMENTO("Desenvolvimento"),
        DEVOLUCAO_AUTOMATICA("Devolução Automática"),
        DEVOLUCAO_AUTOMATICA_DE_PEDIDOS_NAO_USAR_RESTRITO("Devolucao Automatica de Pedidos (Nao Usar Restrito)"),
        DFC("DFC"),
        DIARIOS_CONTABEIS("Diários Contábeis"),
        DIFERENCA_ENTRE_NOTAS_DE_RECLASSIFICACAO("Diferença Entre Notas de Reclassificação"),
        DIFERENCA_NO_VALOR_DA_NOTA("Diferença no Valor da Nota"),
        DIFERENCA_NOS_IMPOSTOS("Diferença Nos Impostos"),
        DIVERG_ENTRE_ATUALIZAR_CUSTO_NA_COMPRA_E_NO_FORN("Diverg. Entre Atualizar Custo na Compra e no Forn."),
        DIVERGENCIA_DE_ESTOQUE_FISICO_FISCAL("Divergência de Estoque Fisico/Fiscal"),
        DLL_EXECUTAVEL_DESATUALIZADO("DLL / Executável Desatualizado"),
        DOCUMENTO_FISCAL_NAO_REFERENCIADO("Documento Fiscal Não Referenciado"),
        DRE("DRE"),
        DROPAR_E_RECRIAR_USUARIO_DO_BANCO_DE_DADOS("Dropar e Recriar Usuario do Banco de Dados"),
        DUPLICIDADE_DE_NOTAS_FISCAIS("Duplicidade de Notas Fiscais"),
        DUPLICIDADE_DE_SUPORTE("Duplicidade de Suporte"),
        DUVIDA_DE_FOLHA_DE_PAGAMENTO("Duvida de Folha de Pagamento"),
        DUVIDAS_ADM_VENDAS("Duvidas Adm Vendas"),
        DUVIDAS_CADASTRO_DE_CFOP("Duvidas Cadastro de CFOP"),
        DUVIDAS_CADASTRO_DE_FORNECEDOR("Duvidas Cadastro de Fornecedor"),
        DUVIDAS_CONHECIMENTO_DE_FRETE("Dúvidas Conhecimento de Frete"),
        DUVIDAS_DE_ACUMULATIVOS("Duvidas de Acumulativos"),
        DUVIDAS_DE_CAIXA("Duvidas de Caixa"),
        DUVIDAS_DE_CAIXAS_OU_DIFERENCA_DE_CAIXA("Duvidas de Caixas Ou Diferença de Caixa"),
        DUVIDAS_DE_COMPRAS("Duvidas de Compras"),
        DUVIDAS_DE_CONTABILIDADE("Duvidas de Contabilidade"),
        DUVIDAS_DE_EMISSAO_DE_NOTAS_FISCAIS("Duvidas de Emissão de Notas Fiscais"),
        DUVIDAS_DE_ENTRADA_DE_NOTA("Duvidas de Entrada de Nota"),
        DUVIDAS_DE_ESTOQUE("Duvidas de Estoque"),
        DUVIDAS_DE_INVENTARIO("Duvidas de Inventario"),
        DUVIDAS_DE_LIBERACAO_DE_ACESSO("Duvidas de Liberacao de Acesso."),
        DUVIDAS_DE_PAGAMENTO_DE_VALE_FRETE_RPA("Duvidas de Pagamento de Vale Frete / RPA"),
        DUVIDAS_DE_RELATORIO_DE_COMISSOES("Duvidas de Relatorio de Comissoes"),
        DUVIDAS_DE_TRANSFERENCIA_DE_MERCADORIA("Duvidas de Transferencia de Mercadoria"),
        DUVIDAS_DE_VENDAS("Duvidas de Vendas"),
        DUVIDAS_DEVOLUCAO_DE_COMPRAS("Duvidas Devolução de Compras"),
        DUVIDAS_DO_ADMPOCKET("Duvidas do Admpocket"),
        DUVIDAS_DO_E_COMMERCE("Duvidas do E-Commerce"),
        DUVIDAS_DO_RAZAO_DE_ESTOQUE("Duvidas do Razao de Estoque"),
        DUVIDAS_EM_DEVOLUCAO_VENDA("Duvidas em Devolução Venda"),
        DUVIDAS_EM_ORDEM_DE_SERICOS("Duvidas Em Ordem de Seriços"),
        DUVIDAS_ENTRADA_PELA_TELA_OUTRAS_NOTAS("Duvidas Entrada Pela Tela Outras Notas"),
        DUVIDAS_FORMACAO_DE_PRECO("Duvidas Formacao de Preço"),
        DUVIDAS_NOS_PROCESSOS_FINANCEIROS("Duvidas Nos Processos Financeiros"),
        DUVIDAS_PROVENIENTES_DO_BOLETIN("Duvidas Provenientes do Boletin"),
        DUVIDAS_REF_A_TRANSFERENCIA_DE_LOCAIS("Duvidas Ref. A Transferencia de Locais"),
        DUVIDAS_RELACIONADAS_AO_CONTROLE_DE_DOF("Duvidas Relacionadas Ao Controle de DOF"),
        DUVIDAS_RELACIONADAS_AO_MANIFESTADOR("Duvidas Relacionadas Ao Manifestador"),
        DUVIDAS_RELATORIOS_DE_COMPRAS("Duvidas Relatorios de Compras"),
        DUVIDAS_RELATORIOS_FISCAIS("Duvidas Relatorios Fiscais"),
        DUVIDAS_SOBRE_CLASSES_FISCAIS_NCM("Duvidas Sobre Classes Fiscais / NCM"),
        DUVIDAS_SOBRE_MIX_DE_VENDAS("Duvidas Sobre Mix de Vendas"),
        DUVIDAS_SOBRE_PIX_OU_PIX("Duvidas Sobre Pix Ou Pix"),
        EMISSAO_DE_NOTA_COMPLEMENTAR_DE_IMPOSTO("Emissão de Nota Complementar de Imposto"),
        EMISSAO_DE_NOTA_DE_IMPORTACAO("Emissão de Nota de Importação"),
        EMISSAO_DE_NOTA_DE_PATRIMONIO("Emissão de Nota de Patrimonio"),
        ENCERRAMENTO_DO_EXERCICIO("Encerramento do Exercício"),
        ENTRADA_DE_NOTA_C_DIVERGENCIA_DE_IMPOSTO("Entrada de Nota C/ Divergencia de Imposto"),
        ENTRADA_DE_NOTAS("Entrada de Notas"),
        ENTRADA_NOTA_DE_IMPORTACAO("Entrada Nota de Importação"),
        ENTRADA_SIMPLES_FATURAMENTO("Entrada Simples Faturamento"),
        ENTRADAS_DE_NOTAS_C_DIVERGENCIA_DE_ICMS("Entradas de Notas C/ Divergencia de ICMS"),
        ENTRADAS_DE_NOTAS_C_DIVERGENCIA_DE_IPI("Entradas de Notas C/ Divergencia de IPI"),
        ENTRADAS_DE_NOTAS_C_DIVERGENCIA_DE_ST("Entradas de Notas C/ Divergencia de ST"),
        ENTRADAS_DE_NOTAS_PRODUTOS_FRACIONADOS("Entradas de Notas Produtos Fracionados"),
        ENTRADAS_DE_NOTAS_SIMPLES_NACIONAL("Entradas de Notas Simples Nacional"),
        ENTREGA_DE_DESENVOLVIMENTO("Entrega de Desenvolvimento"),
        ENVIO_DE_DOCUMENTOS_OU_MANUAIS("Envio de Documentos Ou Manuais"),
        ERRO_ADMASSINATURASDIGITAIS("Erro Admassinaturasdigitais"),
        ERRO_ADMLOGISTICA("Erro Admlogistica"),
        ERRO_ADM_MANIFESTO("Erro Adm Manifesto"),
        ERRO_ADM_VENDAS("Erro Adm Vendas"),
        ERRO_ADMREQUISICOES("Erro Admrequisicoes"),
        ERRO_ADMROTINASSERVER("Erro Admrotinasserver"),
        ERRO_AO_CARREGAR_XML("Erro Ao Carregar XML"),
        ERRO_AO_EXECUTAR_ADM_GERENCIADOR_DE_NOTAS("Erro Ao Executar Adm / Gerenciador de Notas"),
        ERRO_AO_LIBERAR_PEDIDOS_BLOQUEADOS_ORACLE("Erro Ao Liberar Pedidos Bloqueados (Oracle)"),
        ERRO_APOS_ATUALIZACAO("Erro Apos Atualização"),
        ERRO_ARQUIVO_RETORNO_DE_CARTAO("Erro Arquivo Retorno de Cartão"),
        ERRO_COTACAO_WEB("Erro Cotação Web"),
        ERRO_DE_CADASTRO_DE_IMPRESSORA_FISCAL("Erro de Cadastro de Impressora Fiscal"),
        ERRO_DE_CADASTRO_DE_PRODUTOS("Erro de Cadastro de Produtos"),
        ERRO_DE_COMUN_COM_BANCO_DE_DADOS("Erro de Comun. Com Banco de Dados"),
        ERRO_DE_COMUNICACAO_COM_ECF_FISCAL_NAO_FISCAL("Erro de Comunicação Com ECF Fiscal / Nao Fiscal"),
        ERRO_DE_ENVIO_MDF_E("Erro de Envio MDF-E"),
        ERRO_DE_ESTRUTURA_NO_PVA("Erro de Estrutura No Pva"),
        ERRO_DE_IMPRESSAO("Erro de Impressão"),
        ERRO_DE_MENSAGEM_AUTOMATICA("Erro de Mensagem Automatica"),
        ERRO_DE_PK("Erro de Pk"),
        ERRO_DE_PROGRAMACAO("Erro de Programação"),
        ERRO_DE_RECEBIMENTO("Erro de Recebimento"),
        ERRO_DE_RELACAO_DE_INVENTARIO("Erro de Relacao de Inventario"),
        ERRO_DE_SPED_LIVRO_ELETRONICO_DMA("Erro de Sped / Livro Eletronico / DMA"),
        ERRO_DE_SUPORTE("Erro de Suporte"),
        ERRO_DE_TELAS_NO_IMPFISCAL("Erro de Telas No Impfiscal"),
        ERRO_DEXION("Erro Dexion"),
        ERRO_EM_EMISSAO_DE_NOTAS_FISCAIS("Erro Em Emissão de Notas Fiscais"),
        ERRO_EM_OUTRAS_OPERACOES_DE_CAIXA_BANCOS("Erro Em Outras Operações de Caixa/Bancos"),
        ERRO_ENVIO_DE_LOTE_REINF("Erro Envio de Lote Reinf"),
        ERRO_FINANCEIRO("Erro Financeiro"),
        ERRO_MES_NAO_FINALIZADO("Erro Mês Não Finalizado"),
        ERRO_MALA_DIRETA("Erro Mala Direta"),
        ERRO_NA_APURACAO_DE_ICMS("Erro Na Apuração de ICMS"),
        ERRO_NA_TELA_DO_MIX("Erro Na Tela do Mix"),
        ERRO_NFESERVICE("Erro Nfeservice"),
        ERRO_NO_ADM_LOGISTICA("Erro No Adm Logistica"),
        ERRO_NO_BORDERO_DE_COBRANCA("Erro No Bordero de Cobrança"),
        ERRO_NO_FECHAMENTO_DE_ACUMULATIVO("Erro No Fechamento de Acumulativo"),
        ERRO_NO_FINANCEIRO_DO_CARTAO("Erro No Financeiro do Cartão."),
        ERRO_NO_LANCAMENTO_DO_DARE("Erro No Lançamento do Dare"),
        ERRO_NO_MANIFESTADOR("Erro No Manifestador"),
        ERRO_NO_PROCESSO_DE_VENDAS("Erro No Processo de Vendas"),
        ERRO_NO_QUESTIONARIO_DO_ADM("Erro No Questionario do Adm"),
        ERRO_NO_RELATORIO_DE_COMISSAO("Erro No Relatorio de Comissão"),
        ERRO_NO_RELATORIO_DE_VENDAS("Erro No Relatorio de Vendas"),
        ERRO_NO_SPED_CONTABIL_FCONT("Erro No Sped Contabil / Fcont"),
        ERRO_ORA("Erro Ora"),
        ERRO_RECEBIMENTO_DE_PIX("Erro Recebimento de Pix"),
        ERRO_RELACIONADO_AO_FINANCEIRO("Erro Relacionado Ao Financeiro"),
        ERRO_SUGESTAO_DE_COMPRAS("Erro Sugestao de Compras"),
        ERRO_TELA_DE_COMPRAS("Erro Tela de Compras"),
        ERRO_VALE_FRETE("Erro Vale Frete"),
        ERRO_DUVIDAS_REF_AO_RECEITUARIO("Erro/Duvidas Ref. Ao Receituario"),
        ERRO_WHATSAPP("Erro Whatsapp"),
        ERROS_CONHECIMENTO_DE_FRETE("Erros Conhecimento de Frete"),
        ERROS_DE_AUTORIZACOES_FINANCEIRAS("Erros de Autorizações Financeiras"),
        ERROS_DE_IMPLANTACAO("Erros de Implantação"),
        ERROS_DE_VENDA_NO_PDV("Erros de Venda No Pdv"),
        ERROS_PROVENIENTES_DE_MUDANCA_DE_VERSAO("Erros Provenientes de Mudança de Versão"),
        ESTOQUE_MINIMO_MAXIMO("Estoque Minimo/Maximo"),
        EXISTE_ALGUMA_CONTA_A_RECEBER_VENCIDA("Existe Alguma Conta a Receber Vencida"),
        EXISTEM_PROD_NA_COMPRA_COM_QTD_SUP_AO_MAX_EST("Existem Prod. Na Compra Com Qtd Sup. Ao Máx. Est."),
        EXCLUIR_EMPRESA_DO_SISTEMA("Excluir Empresa do Sistema"),
        EXPLICAO_DE_PROCESSOS_DO_ADM("Explicação de Processos do Adm"),
        EXPLICAO_PONTUAIS_DE_PROCESSOS("Explicação Pontuais de Processos"),
        EXTRATO_BANCO("Extrato Banco"),
        FALHA_AO_CADASTRO_CLIENTE("Falha Ao Cadastro Cliente"),
        FALHA_AO_CONSOLIDAR_FINANCEIRO("Falha Ao Consolidar Financeiro"),
        FALHA_AO_DEFINIR_PRODUTO_EM_PONTA_DE_ESTOQUE("Falha Ao Definir Produto Em Ponta de Estoque"),
        FALHA_AO_EMITIR_NFE_NFC_E("Falha Ao Emitir Nfe/Nfc-E"),
        FALHA_AO_ENVIAR_O_EMAIL("Falha Ao Enviar O Email"),
        FALHA_AO_FAZER_DEVOLUCAO_DE_COMPRAS("Falha Ao Fazer Devolução de Compras"),
        FALHA_AO_FAZER_DEVOLUCAO_DE_VENDA("Falha Ao Fazer Devolução de Venda"),
        FALHA_AO_FAZER_LANCAMENTO_DE_ENTRADA("Falha Ao Fazer Lançamento de Entrada"),
        FALHA_AO_FAZER_TRANSFERENCIA_DE_ESTOQUE("Falha Ao Fazer Transferencia de Estoque"),
        FALHA_AO_GERAR_BLOQUEIO_DE_ESTOQUE("Falha Ao Gerar Bloqueio de Estoque"),
        FALHA_AO_GERAR_ENTREGA("Falha Ao Gerar Entrega"),
        FALHA_AO_GERAR_ENTREGA_MANIFESTO("Falha Ao Gerar Entrega/Manifesto"),
        FALHA_AO_GERAR_NOTAS_POR_OUTRAS_NOTAS("Falha Ao Gerar Notas Por Outras Notas"),
        FALHA_AO_GERAR_RETORNO_DE_MANIFESTO("Falha Ao Gerar Retorno de Manifesto"),
        FALHA_AO_GERAR_SEPARACAO("Falha Ao Gerar Separação"),
        FALHA_AO_GERAR_XML("Falha Ao Gerar XML"),
        FALHA_DE_COMUN_COM_IMPRESSORA_FISCAL("Falha de Comun. Com Impressora Fiscal"),
        FALHA_DE_COMUN_COM_IMPRESSORA_LASER_MATRICIAL("Falha de Comun. Com Impressora Laser / Matricial"),
        FALHA_DE_COMUNICACAO_COM_IMPRESSORA_NAO_FISCAL("Falha de Comunicacao Com Impressora Não Fiscal"),
        FALHA_DE_COMUNICACAO_COM_O_TEF("Falha de Comunicacao Com O Tef"),
        FALHA_NA_ABERTUDA_DE_CUPOM("Falha Na Abertuda de Cupom"),
        FALHAS_RELACIONADAS_A_TEF("Falhas Relacionadas A Tef"),
        FALTA_DE_COMUNICACAO_DO_CLIENTE_COM_O_SUPORTE("Falta de Comunicacao Do Cliente Com O Suporte"),
        FALTA_DE_DLL("Falta de Dll"),
        FALTA_DE_INFORMACAO_DO_DOCUMENTO("Falta de Informação do Documento"),
        FEEDBACK("Feedback"),
        FINANCEIRO_NAO_GERADO("Financeiro Não Gerado"),
        FISCAL("Fiscal"),
        FITA_DETALHE("Fita Detalhe"),
        FLUXO_DE_CAIXA("Fluxo de Caixa"),
        FORMATAO_MONTAGEM("Formatação / Montagem"),
        FUNCIONARIOS("Funcionários"),
        GERAR_DOC_FISCAL("Gerar Doc Fiscal"),
        GERAR_EXE("Gerar Exe"),
        GERENCIA_DE_ANTIVIRUS("Gerencia de Antivirus"),
        GERENCIA_DE_BANCO_DE_DADOS("Gerencia de Banco de Dados"),
        GNRE("GNRE"),
        HABILITAR_GERENCIAMENTO_DE_FERIAS("Habilitar Gerenciamento de Férias"),
        HISTORICO_CONTABIL("Historico Contábil"),
        HOMOLOGACAO_DE_BOLETOS("Homologação de Boletos"),
        HOMOLOGACAO_REMESSA_CHEQUES("Homologacao Remessa Cheques"),
        HORARIO("Horário"),
        IMPFISCAL_ERRO_NA_IMPRESSAO_DO_ITEN("Impfiscal Erro Na Impressao Do Iten"),
        IMPLANTACAO_ADM("Implantacao Adm"),
        IMPLANTACAO_CONTROLE_DE_CEP("Implantacao Controle de Cep"),
        IMPLANTACAO_DA_CONTABILIDADE("Implantação da Contabilidade"),
        IMPLANTACAO_DE_NOVA_EMPRESA("Implantação de Nova Empresa"),
        IMPLANTACAO_DE_PROCESSO("Implantação de Processo"),
        IMPLANTACAO_DE_VENDA_DIRETA("Implantacao de Venda Direta"),
        IMPORTACAO_CONTABILIDADE("Importacao Contabilidade"),
        IMPORTACAO_DE_DADOS("Importação de Dados"),
        IMPORTACAO_DE_DADOS_DE_ADM_PARA_ADM("Importacao de Dados de Adm Para Adm"),
        IMPORTACAO_DE_FOLHA_DE_PAGAMENTO("Importação de Folha de Pagamento"),
        IMPORTACAO_PARA_SPED("Importacao Para Sped"),
        IMPORTACAO_XML_DE_SISTEMA_ANTERIOR_P_ADM("Importacao XML de Sistema Anterior P/ Adm"),
        IMPRESSAO_DE_ORCAMENTOS_PROPOSTAS("Impressao de Orçamentos/Propostas"),
        IMPRESSORA_PARADA_POR_TIRAR_REDUCAO_Z("Impressora Parada Por Tirar Reducao Z"),
        INADIMPLENCIA("Inadimplência"),
        INCONSISTENCIA_DE_CUPOM_FISCAL("Inconsistencia de Cupom Fiscal"),
        INCONSISTENCIA_DE_EXPORTACAO_LIVRO_FISCAL("Inconsistencia de Exportação Livro Fiscal"),
        INCONSISTENCIAS_NA_APURACAO("Inconsistencias Na Apuracao"),
        INSTALACAO_AUXILIO_A_DEMAIS_SOFTWARES("Instalação / Auxílio a Demais Softwares"),
        INSTALACAO_ADM_CONTIGENCIA("Instalacao Adm Contigencia"),
        INSTALACAO_DE_EQUIPAMENTOS("Instalação de Equipamentos"),
        INSTALACAO_DE_ORACLE("Instalação de Oracle"),
        INSTALACAO_DE_SOFTWARE("Instalação de Software"),
        INSTALACAO_DO_ADM("Instalação do ADM"),
        INSTALACAO_DO_CERTIFICADO_DIGITAL("Instalacao Do Certificado Digital"),
        INSTALACAO_DO_LOGMEIN("Instalação do Logmein"),
        INSTALACAO_E_CONFIGURACAO_TEF_ADM("Instalacao e Configuracao Tef Adm"),
        INSTALACAO_MD_E("Instalacao Md-E"),
        INSTALAR_ADM_MOBILE("Instalar Adm Mobile"),
        INSTALAR_SMS("Instalar SMS"),
        INTEGRACAO_DE_COMPRAS("Integração de Compras"),
        INTEGRACAO_E_COMMERCE("Integracao E-Commerce"),
        INTEGRACAO_FISCAL("Integração Fiscal"),
        INTEGRACAO_NEOGRID("Integracao Neogrid"),
        LANCAMENTO_DE_REDUCAO_Z("Lançamento de Redução Z"),
        LANCAMENTO_MANUAL_DE_CAIXAS_BANCOS("Lançamento Manual de Caixas/Bancos"),
        LANCAMENTOS_CONTABEIS("Lançamentos Contábeis"),
        LEITURA_DOC_DA_VERSAO("Leitura Doc Da Versao"),
        LENTIDAO_DE_BANCO_DE_DADOS("Lentidão de Banco de Dados"),
        LENTIDAO_DE_PROCESSOS("Lentidão de Processos"),
        LEVANTAMENTO_DE_DADOS("Levantamento de Dados"),
        LIBERACAO_DE_PROCESSO_DE_BLOQUEIOS_DE_VENDA("Liberação de Processo de Bloqueios de Venda"),
        LIMITE_DE_CAIXA_POR_GRUPO_DE_PROD_EXCEDIDO("Limite de Caixa Por Grupo de Prod. Excedido"),
        LIMITE_DE_COMPETENCIA_POR_GRUPO_DE_PROD_EXCEDIDO("Limite de Competência Por Grupo de Prod. Excedido"),
        LIMITE_DE_COMPRA_MENSAL_EXCEDIDO("Limite de Compra Mensal Excedido"),
        LIMITE_DE_ENDIVIDAMENTO_MENSAL_DE_COMPRA_EXCEDIDO("Limite de Endividamento Mensal de Compra Excedido"),
        LISTA_DE_SEPARACAO("Lista de Separação"),
        LIVRO_RAZAO("Livro Razão"),
        LOCAL_DE_ESTOQUE("Local de Estoque"),
        MANUTENCAO_DE_BANCO_DE_DADOS("Manutenção de Banco de Dados"),
        MANUTENCAO_LINK_COMUNICACAO("Manutenção Link Comunicação"),
        MANUTENCAO_MENSAL_TECNOLOGIA("Manutencao Mensal Tecnologia"),
        MAPA_FINANCEIRO("Mapa Financeiro"),
        MARKETING_COMERCIAL("Marketing / Comercial"),
        MODULOS_CONTRATO_PELO_CLIENTE("Modulos Contrato Pelo Cliente"),
        MUDANCA_DE_DEPARTAMENTO("Mudança de Departamento"),
        MUDANCA_DE_DESCONTOS_NA_COMPRA("Mudança de Descontos Na Compra"),
        MUDANCA_DE_PRAZO_DE_ENTREGA("Mudança de Prazo de Entrega"),
        MUDANCA_DE_PRAZOS_DE_FRETE_NA_COMPRA("Mudança de Prazos de Frete Na Compra"),
        MUDANCA_DE_PRAZOS_NA_COMPRA("Mudança de Prazos Na Compra"),
        MUDANCA_DE_REGIME_DE_TRIBUTACAO("Mudança de Regime de Tributacao"),
        MUDANCA_DE_VALOR_DE_FRETE_NA_COMPRA("Mudança de Valor de Frete Na Compra"),
        NAO_ESTA_IMPRIMINDO("Não Está Imprimindo"),
        NAO_USAR_NO_CRS_ANALISE_DE_PROGRAMACAO("Nao Usar No CRS Analise de Programacao"),
        NAO_USAR_NO_CRS_CONFIGURACAO_DE_ATALHO("Não Usar No Crs Configuração de Atalho"),
        NAO_UTILIZAR("Nao Utilizar"),
        NORMAS_E_REGRAS_INTERNAS("Normas e Regras Internas"),
        NOTAS_DENEGADAS("Notas Denegadas"),
        ORGANIZACAO_DE_AMBIENTE_FISICO("Organização de Ambiente Fisico"),
        ORIENTACAO_AUTORIZACOES_DE_TELAS_E_PROCESSOS("Orientação (Autorizações de Telas e Processos)"),
        ORIENTACOES_REF_A_LEITORES_DE_CODIGO_DE_BARRAS("Orientações Ref A Leitores de Codigo de Barras"),
        PAGAMENTO_ELETRONICO("Pagamento Eletronico"),
        PARAMETIZAR_MANIFESTO_DE_TRANSPORTE("Parametizar Manifesto de Transporte"),
        PARAMETRIZACAO_DE_PROCESSOS("Parametrização de Processos"),
        PARAMETROS_DE_CONTABILIDADE("Parametros de Contabilidade"),
        PARAMETROS_DE_ESTACAO("Parametros de Estacao"),
        PED_COMPRA_COM_QTD_INSUF_PARA_ATENDER_PED_VENDAS("Ped.Compra Com Qtd Insuf. Para Atender Ped.Vendas"),
        PEGAR_BACKUP_IMPORTAR_BACKUP("Pegar Backup / Importar Backup"),
        PEGAR_LOGS_DE_IMPRESSORAS("Pegar Logs de Impressoras"),
        PEGAR_TABELA_IBPT("Pegar Tabela Ibpt"),
        PENDENCIAS_DE_IMPLANTACAO("Pendencias de Implantação"),
        PENDENCIAS_DE_SOLICITACOES("Pendências de Solicitações"),
        PERC_FRETE_DO_FORNECEDOR_NAO_UTILIZADO_NA_COMPRA("Perc Frete Do Fornecedor Não Utilizado Na Compra"),
        PIX_LINK_DE_PAGAMENTO("Pix Link de Pagamento"),
        PIX_QR_CODE_EM_TELA("Pix Qr Code Em Tela"),
        PLANO_FINANCEIRO("Plano Financeiro"),
        POS_ATENDIMENTO_LOGMEIN("Pós Atendimento (Logmein)"),
        POS_ATENDIMENTO_SANTRI("Pós-Atendimento Santri"),
        POSICIONAMENTO_DE_SOLICITACOES("Posicionamento de Solicitações"),
        PRIMEIRO_TRANSFERENCIA_DE_ATENDIMENTO("Primeiro (Transferencia de) Atendimento"),
        PROBLEMAS_COM_OUTROS_SOFTWARE("Problemas Com Outros Software"),
        PROBLEMAS_DE_CONEXAO_DE_REDE_VPN("Problemas de Conexao de Rede / VPN"),
        PROBLEMAS_DE_SERVIDOR("Problemas de Servidor"),
        PROBLEMAS_FISICOS_COM_SERVIDOR("Problemas Fisicos Com Servidor"),
        PROBLEMAS_RECORRENTES("Problemas Recorrentes"),
        PROBLEMAS_RELACIONADOS_A_CAMERAS_DVR_S("Problemas Relacionados A Cameras/Dvr'S"),
        PROBLEMAS_RELACIONADOS_A_CONTVER("Problemas Relacionados A Contver"),
        PROCESSO_CONTABIL("Processo Contábil"),
        PROCESSO_DE_MADEIRA("Processo de Madeira"),
        PROCESSO_DE_PERDA_ROUBO("Processo de Perda/Roubo"),
        PROCESSO_DE_PRE_ENTRADA("Processo de Pré-Entrada"),
        PROMOCOES_DE_VENDAS("Promocoes de Vendas"),
        QUEDA_DE_ENERGIA("Queda de Energia"),
        QUEDA_DE_LINK_DE_RADIO("Queda de Link de Rádio"),
        RECARGA_DE_CELULAR("Recarga de Celular"),
        RECLAMACAO_DE_ATENDIMENTO("Reclamação de Atendimento"),
        RECLASSIFICACAO("Reclassificação"),
        RECUPERACAO_DE_DADOS("Recuperação de Dados"),
        REDUCAO_Z_NAO_ESTA_GRAVANDO_NO_ADM("Redução Z Nao Esta Gravando No Adm"),
        REGRAS_DE_FIREWALL("Regras de Firewall"),
        REJEICAO_CBENEF("Rejeição Cbenef"),
        REJEICAO_CST_INCOMPATIVEL_COM_NAO_CONTRIBUINTE("Rejeição Cst Incompativel Com Não Contribuinte"),
        REJEICAO_203_EMISSOR_NAO_HABILITADO("Rejeição 203 - Emissor Não Habilitado"),
        REJEICAO_306_IE_DO_DESTINATARIO_NAO_ATIVA_NA_UF("Rejeição 306 - IE Do Destinatario Não Ativa Na UF"),
        REJEICAO_468_NFE_COM_TIPO_EMISSAO_4_SEM_EPEC("Rejeição 468 - Nfe Com Tipo Emissão=4, Sem Epec"),
        REJEICAO_501_PRAZO_DE_CANCELAMENTO_SUPERIOR("Rejeição 501 - Prazo de Cancelamento Superior"),
        REJEICAO_521_UF_DO_EMITENTE_DIFERE_DA_UF_DO_DEST("Rejeição 521 - Uf Do Emitente Difere Da Uf Do Dest"),
        REJEICAO_656_CONSUMO_INDEVIDO("Rejeição 656 - Consumo Indevido"),
        REJEICAO_778_INFORMADO_NCM_INEXISTENTE("Rejeição 778 - Informado Ncm Inexistente"),
        REJEICAO_799("Rejeição 799"),
        RELATORIO_DE_CONTAS_A_PAGAR("Relatorio de Contas a Pagar"),
        RELATORIO_DE_CONTAS_A_RECEBER("Relatorio de Contas a Receber"),
        RELATORIO_DE_PRODUTOS("Relatorio de Produtos"),
        RELATORIO_PAF_ECF("Relatorio Paf-Ecf"),
        REMANEJAMENTO_DE_COMPUTADORES_EQUIPAMENTOS("Remanejamento de Computadores / Equipamentos"),
        REMESSA_E_RETORNO_DE_BOLETOS("Remessa e Retorno de Boletos"),
        REMESSA_RETORNO_DE_MERC_PARA_CONSERTO_TROCA("Remessa / Retorno de Merc Para Conserto / Troca"),
        REMESSAS_EM_ABERTO_COM_PREVISAO_DE_RETORNO_VENCIDA("Remessas Em Aberto Com Previsão de Retorno Vencida"),
        REQUISICAO_DE_DESPESAS("Requisição de Despesas"),
        RETORNO_DE_CONEXAO_PERDIDA("Retorno de Conexao Perdida"),
        RETORNO_DE_CONTATO_AO_CLIENTE("Retorno de Contato Ao Cliente"),
        RETORNO_DE_ENTRADA_DE_NOTA("Retorno de Entrada de Nota"),
        RETORNO_DE_SPED_LIVRO_DMA("Retorno de Sped / Livro / DMA"),
        RETORNO_DE_SUPORTE_TECNICO("Retorno de Suporte Técnico"),
        RETORNO_FINANCEIRO("Retorno Financeiro"),
        RETORNO_NUCLEO_02("Retorno Nucleo 02"),
        RETORNO_NUCLEO_1("Retorno Nucleo 1"),
        RETORNO_PROCESSO_FISCAL("Retorno Processo Fiscal"),
        RODAR_PROCEDIMENTOS("Rodar Procedimentos"),
        RONDA_TECNICA("Ronda Técnica"),
        ROTINA_DE_BACKUP_ADM("Rotina de Backup Adm"),
        SEFAZ_FORA_DO_AR("Sefaz Fora Do Ar"),
        SELECT_BANCO_DE_DADOS("Select Banco de Dados"),
        SENHA_DO_BANCO_EXPIRADA("Senha Do Banco Expirada"),
        SERVIDOR_COTACAO_WEB("Servidor Cotação Web"),
        SERVIDOR_STANDBY_ORACLE("Servidor Standby Oracle"),
        SOLICITACAO_DE_COLABORADOR_SANTRI("Solicitação de Colaborador Santri"),
        SPED_ECF("Sped Ecf"),
        SUGESTAO_DE_MELHORIAS("Sugestao de Melhorias"),
        SUPORTE("Suporte"),
        SUPORTE_POR_EMAIL("Suporte Por Email"),
        SUPORTE_TECNICO_EXTERNO("Suporte Técnico Externo"),
        SUPORTE_TECNICO_INTERNO("Suporte Tecnico Interno"),
        T_I_SANTRI("T.I Santri"),
        TINTOMETRICO("Tintometrico"),
        TIPO_DE_FRETE_DIFERENTE_DO_CADASTRO_DO_FORNECEDOR("Tipo de Frete Diferente Do Cadastro Do Fornecedor"),
        TRANCAMENTO_CONTABIL("Trancamento Contábil"),
        TRANSFERENCIA_DE_SALDO("Transferencia de Saldo"),
        TRANSFERENCIAS_ENTRE_EMPRESAS("Transferencias Entre Empresas"),
        TRANSPORTADORA_DIF_DO_CADASTRO_DO_FORNECEDOR("Transportadora Dif. Do Cadastro Do Fornecedor"),
        TREINAMENTO("Treinamento"),
        TREINAMENTO_COM_CERTIFICADOS("Treinamento Com Certificados"),
        TREINAMENTOS("Treinamentos"),
        TROCA_DE_DLL_EXECUTAVEL("Troca de DLL / Executável"),
        TROCA_DE_DLL_EXECUTAVEL_ERRO_DE_PROGRAMACAO("Troca de Dll / Executavel, Erro de Programação"),
        TROCA_DE_DLL_REF_AO_IVA("Troca de Dll Ref. Ao Iva"),
        TROCA_DE_PERIFERICOS("Troca de Perifericos"),
        TROCA_DE_SERVIDOR_DE_DADOS("Troca de Servidor de Dados"),
        TROCA_DE_TIPO_DE_LUCRO_LIQUIDO_BRUTO_MARKUP("Troca de Tipo de Lucro (Liquido, Bruto, Markup)"),
        TROCAR_SENHAS_DO_BANCO_DE_DADOS("Trocar Senhas Do Banco de Dados"),
        UNIFICACAO_SEPARACAO_DE_EMPRESAS("Unificacao/Separação de Empresas");
        
        private final String displayValue;
        
        TipoDeOcorrencia(String displayValue) {
            this.displayValue = displayValue;
        }
    
        public String getDisplayValue() {
            return displayValue;
        }
    }

    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getEmpresaCliente() { return empresaCliente; }
    public void setEmpresaCliente(String empresaCliente) { this.empresaCliente = empresaCliente; }

    public String getNomeCliente() { return nomeCliente; }
    public void setNomeCliente(String nomeCliente) { this.nomeCliente = nomeCliente; }

    public LocalDateTime getDataHoraAtendimento() { return dataHoraAtendimento; }
    public void setDataHoraAtendimento(LocalDateTime dataHoraAtendimento) { this.dataHoraAtendimento = dataHoraAtendimento; }

    public String getMotivoContato() { return motivoContato; }
    public void setMotivoContato(String motivoContato) { this.motivoContato = motivoContato; }

    public String getSolucaoPassada() { return solucaoPassada; }
    public void setSolucaoPassada(String solucaoPassada) { this.solucaoPassada = solucaoPassada; }

    public String getNumeroCRS() { return numeroCRS; }
    public void setNumeroCRS(String numeroCRS) { this.numeroCRS = numeroCRS; }

    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }

    public String getAnydesk() { return anydesk; }
    public void setAnydesk(String anydesk) { this.anydesk = anydesk; }

    public CanalAtendimento getCanalAtendimento() { return canalAtendimento; }
    public void setCanalAtendimento(CanalAtendimento canalAtendimento) { this.canalAtendimento = canalAtendimento; }

    public TipoDeOcorrencia getTipoDeOcorrencia() { return tipoDeOcorrencia; }
    public void setTipoDeOcorrencia(TipoDeOcorrencia tipoDeOcorrencia) { this.tipoDeOcorrencia = tipoDeOcorrencia; }

    public Usuario getUsuarioAtendente() { return usuarioAtendente; }
    public void setUsuarioAtendente(Usuario usuarioAtendente) { this.usuarioAtendente = usuarioAtendente; }

        public Boolean getAtendimentoTransferencia() {
        return atendimentoTransferencia;
    }

    public void setAtendimentoTransferencia(Boolean atendimentoTransferencia) {
        this.atendimentoTransferencia = atendimentoTransferencia;
    }

    public String getProtocoloAtendimento() {
        return protocoloAtendimento;
    }

    public void setProtocoloAtendimento(String protocoloAtendimento) {
        this.protocoloAtendimento = protocoloAtendimento;
    }
}