<!-- FreeMarker macros have to be imported into a namespace.
    We strongly recommend sticking to 'spring'. -->
<#import "spring.ftl" as spring/>
<html>
<head>
    <meta http-equiv="Cache-Control" content="no-cache" />
    <meta http-equiv="content-type" content="text/html; charset=utf-8">
    <title>${name}</title>
</head>
<body>
    Hello ${student.name}!
</body>
</html>