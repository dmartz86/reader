package co.com.yuxipacific.library

import org.dom4j.Document
import org.dom4j.Element
import org.dom4j.Attribute
import org.dom4j.io.SAXReader
import org.dom4j.io.XMLWriter
import org.dom4j.xpath.DefaultXPath
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.web.multipart.MultipartHttpServletRequest
import org.springframework.web.multipart.commons.CommonsMultipartFile
import org.xml.sax.InputSource
import grails.converters.*
import javax.xml.parsers.DocumentBuilder

class BookController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [bookInstanceList: Book.list(params), bookInstanceTotal: Book.count()]
    }

    def create() {
        [bookInstance: new Book(params)]
    }

    def save() {
        def bookInstance = new Book(params)
        if (!bookInstance.save(flush: true)) {
            render(view: "create", model: [bookInstance: bookInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'book.label', default: 'Book'), bookInstance.id])
        redirect(action: "show", id: bookInstance.id)
    }

    def show(Long id) {
        def bookInstance = Book.get(id)
        if (!bookInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'book.label', default: 'Book'), id])
            redirect(action: "list")
            return
        }

        [bookInstance: bookInstance]
    }

    def edit(Long id) {
        def bookInstance = Book.get(id)
        if (!bookInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'book.label', default: 'Book'), id])
            redirect(action: "list")
            return
        }

        [bookInstance: bookInstance]
    }

    def update(Long id, Long version) {
        def bookInstance = Book.get(id)
        if (!bookInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'book.label', default: 'Book'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (bookInstance.version > version) {
                bookInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'book.label', default: 'Book')] as Object[],
                          "Another user has updated this Book while you were editing")
                render(view: "edit", model: [bookInstance: bookInstance])
                return
            }
        }

        bookInstance.properties = params

        if (!bookInstance.save(flush: true)) {
            render(view: "edit", model: [bookInstance: bookInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'book.label', default: 'Book'), bookInstance.id])
        redirect(action: "show", id: bookInstance.id)
    }

    def delete(Long id) {
        def bookInstance = Book.get(id)
        if (!bookInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'book.label', default: 'Book'), id])
            redirect(action: "list")
            return
        }

        try {
            bookInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'book.label', default: 'Book'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'book.label', default: 'Book'), id])
            redirect(action: "show", id: id)
        }
    }
	
	/**
	 * The view to upload the XML database
	 * @return
	 */
	def xml(){
		action: "xml"
	}
	
	/**
	 * Upload  the xml and save into the men database
	 * 
	 * @return
	 */
	def upload() {
		
		try {
			def booksXML = request.getFile("xmlData").inputStream.text
			def records = new XmlParser().parseText(booksXML)
			
			for(int i=0 ; i< records.Book.size() ; i++){
				def book = records.Book[i]
				println() 
				def bookInstance = new Book(
						author: book.Author[0].text() ,
						country: book.Country[0].text() ,  
						language: book.Language[0].text() ,  
						price:  (book.Price[0].text()) as int , 
						tittle: book.Title[0].text() , 
						quantity: book.Quantity[0].text() as int
					)
				
				if (!bookInstance.save(flush: true)) {
					redirect(action: "list")
					return
				}
			}

			redirect(action: "list")
		}
		catch (DataIntegrityViolationException e) {
			flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'book.label', default: 'Book'), id])
			redirect(action: "xml", id: id)
		}
	}
}

