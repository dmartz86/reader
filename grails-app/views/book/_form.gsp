<%@ page import="co.com.yuxipacific.library.Book" %>



<div class="fieldcontain ${hasErrors(bean: bookInstance, field: 'author', 'error')} ">
	<label for="author">
		<g:message code="book.author.label" default="Author" />
		
	</label>
	<g:textField name="author" value="${bookInstance?.author}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bookInstance, field: 'country', 'error')} ">
	<label for="country">
		<g:message code="book.country.label" default="Country" />
		
	</label>
	<g:textField name="country" value="${bookInstance?.country}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bookInstance, field: 'language', 'error')} ">
	<label for="language">
		<g:message code="book.language.label" default="Language" />
		
	</label>
	<g:textField name="language" value="${bookInstance?.language}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bookInstance, field: 'price', 'error')} required">
	<label for="price">
		<g:message code="book.price.label" default="Price" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="price" type="number" value="${bookInstance.price}" required=""/>
</div>

<div class="fieldcontain ${hasErrors(bean: bookInstance, field: 'quantity', 'error')} required">
	<label for="quantity">
		<g:message code="book.quantity.label" default="Quantity" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="quantity" type="number" value="${bookInstance.quantity}" required=""/>
</div>

<div class="fieldcontain ${hasErrors(bean: bookInstance, field: 'tittle', 'error')} ">
	<label for="tittle">
		<g:message code="book.tittle.label" default="Tittle" />
		
	</label>
	<g:textField name="tittle" value="${bookInstance?.tittle}"/>
</div>

