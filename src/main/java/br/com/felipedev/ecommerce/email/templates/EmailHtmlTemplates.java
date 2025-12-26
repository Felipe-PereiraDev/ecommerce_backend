package br.com.felipedev.ecommerce.email.templates;

public final class EmailHtmlTemplates {
    private EmailHtmlTemplates() {}

    public static String buildAccountVerificationEmailHtml(String name, String link) {
        return """
        <!DOCTYPE html>
        <html lang="pt-BR">
        <head>
            <meta charset="UTF-8">
            <title>Verificação de Email</title>
        </head>
        <body style="margin:0; padding:0; background-color:#f4f6f8; font-family:Arial, Helvetica, sans-serif;">

            <table width="100%%" cellpadding="0" cellspacing="0" style="padding:20px;">
                <tr>
                    <td align="center">
                        <table width="600" cellpadding="0" cellspacing="0"
                               style="background-color:#ffffff; border-radius:8px; padding:30px;
                               box-shadow:0 2px 8px rgba(0,0,0,0.08);">

                            <!-- Título -->
                            <tr>
                                <td style="text-align:center; padding-bottom:20px;">
                                    <h2 style="margin:0; color:#333;">Verificação de Conta</h2>
                                </td>
                            </tr>

                            <!-- Saudação -->
                            <tr>
                                <td style="color:#555; font-size:16px; padding-bottom:15px;">
                                    Olá <strong>%s</strong>,
                                </td>
                            </tr>

                            <!-- Texto principal -->
                            <tr>
                                <td style="color:#555; font-size:15px; line-height:1.6; padding-bottom:20px;">
                                    Para concluir a verificação da sua conta, clique no botão abaixo.
                                    Esse passo é importante para garantir a segurança do seu acesso.
                                </td>
                            </tr>

                            <!-- Aviso de expiração -->
                            <tr>
                                <td style="color:#b91c1c; font-size:14px; padding-bottom:25px;">
                                    ⚠️ Este link é válido por <strong>15 minutos</strong>.
                                </td>
                            </tr>

                            <!-- Botão -->
                            <tr>
                                <td align="center" style="padding-bottom:25px;">
                                    <a href="%s"
                                       style="background-color:#4f46e5; color:#ffffff; text-decoration:none;
                                       padding:14px 28px; border-radius:6px; font-size:16px;
                                       font-weight:bold; display:inline-block;">
                                        Verificar Conta
                                    </a>
                                </td>
                            </tr>

                            <!-- Link alternativo -->
                            <tr>
                                <td style="color:#777; font-size:14px; line-height:1.5;">
                                    Se você não conseguir clicar no botão, copie e cole o link abaixo no seu navegador:
                                    <br><br>
                                    <a href="%s" style="color:#4f46e5; word-break:break-all;">
                                        %s
                                    </a>
                                </td>
                            </tr>

                            <!-- Rodapé -->
                            <tr>
                                <td style="padding-top:30px; font-size:12px; color:#999; text-align:center;">
                                    © 2025 Ecommerce XNada. Todos os direitos reservados.<br>
                                    Este é um e-mail automático, não responda.
                                </td>
                            </tr>

                        </table>
                    </td>
                </tr>
            </table>

        </body>
        </html>
        """.formatted(name, link, link, link);
    }

    public static String buildPasswordChangeNotificationEmailHtml(String name) {
        return """
            <!DOCTYPE html>
            <html lang="pt-BR">
            <head>
                <meta charset="UTF-8">
                <title>Aviso de Segurança</title>
            </head>
            <body style="margin:0; padding:0; background-color:#f4f6f8; font-family:Arial, Helvetica, sans-serif;">

            <table width="100%%" cellpadding="0" cellspacing="0" style="padding:20px;">
                <tr>
                    <td align="center">
                        <table width="600" cellpadding="0" cellspacing="0"
                               style="background-color:#ffffff; border-radius:8px; padding:30px;
                               box-shadow:0 2px 8px rgba(0,0,0,0.08);">

                            <!-- Título -->
                            <tr>
                                <td style="text-align:center; padding-bottom:20px;">
                                    <h2 style="margin:0; color:#333;">Aviso de Segurança</h2>
                                </td>
                            </tr>

                            <!-- Saudação -->
                            <tr>
                                <td style="color:#555; font-size:16px; padding-bottom:15px;">
                                    Olá <strong>%s</strong>,
                                </td>
                            </tr>

                            <!-- Texto principal -->
                            <tr>
                                <td style="color:#555; font-size:15px; line-height:1.6; padding-bottom:20px;">
                                    Identificamos que você não altera sua senha há
                                    <strong>90 dias</strong>.
                                    Manter sua senha atualizada é uma prática importante para garantir a segurança da sua conta.
                                </td>
                            </tr>

                            <!-- Aviso informativo -->
                            <tr>
                                <td style="color:#1f2937; background-color:#f1f5f9; border-radius:6px;
                                           padding:14px; font-size:14px; margin-bottom:20px;">
                                    🔐 Recomendamos que você revise sua senha periodicamente e evite reutilizar senhas antigas.
                                </td>
                            </tr>

                            <!-- Observação -->
                            <tr>
                                <td style="padding-top:15px; font-size:13px; color:#6b7280;">
                                    Este é apenas um aviso informativo.
                                    Caso você já tenha alterado sua senha recentemente, pode desconsiderar esta mensagem.
                                </td>
                            </tr>

                            <!-- Rodapé -->
                            <tr>
                                <td style="padding-top:30px; font-size:12px; color:#999; text-align:center;">
                                    © 2025 SuaApp. Todos os direitos reservados.<br>
                                    Este é um e-mail automático, não responda.
                                </td>
                            </tr>

                        </table>
                    </td>
                </tr>
            </table>

            </body>
            </html>
            """.formatted(name);
    }

}
