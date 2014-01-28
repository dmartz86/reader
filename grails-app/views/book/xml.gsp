
<%@ page import="co.com.yuxipacific.library.Book"%>
<!DOCTYPE html>
<html>
<head>
<meta name="layout" content="main">
<g:set var="entityName"
	value="${message(code: 'book.label', default: 'Book')}" />
<title>XML Uploader</title>
</head>
<body>
	<a href="#list-book" class="skip" tabindex="-1"><g:message
			code="default.link.skip.label" default="Skip to content&hellip;" /></a>
	<div class="nav" role="navigation">
		<ul>
			<li><a class="home" href="${createLink(uri: '/')}"><g:message
						code="default.home.label" /></a></li>
			<li><g:link class="create" action="create">
					<g:message code="default.new.label" args="[entityName]" />
				</g:link></li>
		</ul>
	</div>
	<div id="uploaderId" class="content scaffold-list" role="main">
		<h1>Load your XML database</h1>
		<g:if test="${flash.message}">
			<div class="message" role="status">
				${flash.message}
			</div>
		</g:if>
		
		<div>
			<g:uploadForm  action="upload" method="post" controller="Book" ectype="multipart/form-data">
				<label for="uploadInput">Select your xml file:</label>
				<input type="file" name="xmlData"/>
        		<input type="submit" value="Go!" />
			</g:uploadForm>
		</div>
	</div>
</body>
</html>
