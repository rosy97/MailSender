# MailSender
1. 构建Java发送邮件的工具类：MailServiceUtils

2. 通过MailController测试邮件发送

  simpleSend() 入参说明：

    @RequestParam String subject, //主题

    @RequestParam String content, //内容

    @RequestParam String[] receivers, //接收者

    String[] cc,  //抄送者

    String[] bcc,  //密送者

    String[] attaches //附件filePath
