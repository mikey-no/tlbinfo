package controllers;

import java.util.concurrent.CompletionStage;

import models.TLB;
import play.data.Form;
import play.data.FormFactory;
import play.libs.Json;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Results;
import repository.TLBRepository;

import javax.inject.Inject;
import javax.persistence.PersistenceException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletionStage;

/**
 * Manage a database of tlbs
 */
public class TlbController extends Controller {

	private final TLBRepository tlbRepository;
	private final FormFactory formFactory;
	private final HttpExecutionContext httpExecutionContext;
	private final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger("application");

	@Inject
	public TlbController(FormFactory formFactory, TLBRepository tlbRepository,
			HttpExecutionContext httpExecutionContext) {
		this.tlbRepository = tlbRepository;
		this.formFactory = formFactory;
		this.httpExecutionContext = httpExecutionContext;
	}

	/**
	 * This result directly redirect to application home.
	 */
	private Result GO_HOME = Results.redirect(routes.TlbController.list(0, "name", "asc", ""));

	/**
	 * Display the paginated list of tlb.
	 *
	 * @param page
	 *            Current page number (starts from 0)
	 * @param sortBy
	 *            Column to be sorted
	 * @param order
	 *            Sort order (either asc or desc)
	 * @param filter
	 *            Filter applied on tlb names
	 */
	public CompletionStage<Result> list(int page, String sortBy, String order, String filter) {
		// Run a db operation in another thread (using DatabaseExecutionContext)
		return tlbRepository.page(page, 10, sortBy, order, filter).thenApplyAsync(list -> {
			// This is the HTTP rendering thread context
			return ok(views.html.tlblist.render(list, sortBy, order, filter));
		}, httpExecutionContext.current());
	}

	/**
	 * Display the 'new tlb form'.
	 */
	public Result create() {
		Form<TLB> tlbForm = formFactory.form(TLB.class);
		// This is the HTTP rendering thread context
		return ok(views.html.tlbCreateForm.render(tlbForm));

	}

	/**
	 * Handle the 'new tlb form' submission (blocking)
	 */
	public Result save() {
		Form<TLB> tlbForm = formFactory.form(TLB.class).bindFromRequest();

		if (tlbForm.hasErrors()) {
			logger.debug("TlbController: save(): got here with errors : " + tlbForm.allErrors());
			return badRequest(views.html.tlbCreateForm.render(tlbForm));
		}
		TLB tlb = tlbForm.get();
		// Run insert db operation, then redirect
		tlbRepository.insert(tlb);
		flash("success", "Tlb " + tlb.name + " has been created");
		return GO_HOME;
	}

	/**
	 * Provide a full list of tlbs in Json format (asynchronously)
	 */
	public CompletionStage<Result> listAllJson() {
		// Run a db operation in another thread (using DatabaseExecutionContext)
		return tlbRepository.findAll().thenApplyAsync(tlbList -> {
			// This is the HTTP rendering thread context
			return ok(Json.toJson(tlbList));
		}, httpExecutionContext.current());
	}

//	/**
//	 * Handle the 'new tlb form' submission
//	 */
//	public CompletionStage<Result> save() {
//		Form<Tlb> tlbForm = formFactory.form(Tlb.class).bindFromRequest();
//		if (tlbForm.hasErrors()) {
//			// Run companies db operation and then render the form
//			 return tlbRepository.options().thenApplyAsync(companies -> {
//			// This is the HTTP rendering thread context
//			return badRequest(views.html.createForm.render(computerForm, companies));
//			 }, httpExecutionContext.current());
//		}
//
//		Tlb tlb = tlbForm.get();
//		// Run insert db operation, then redirect
//		return tlbRepository.insert(tlb).thenApplyAsync(data -> {
//			// This is the HTTP rendering thread context
//			flash("success", "Tlb " + tlb.name + " has been created");
//			return GO_HOME;
//		}, httpExecutionContext.current());
//	}
	
    /**
     * Handle tlb deletion
     */
    public CompletionStage<Result> delete(Long id) {
        // Run delete db operation, then redirect
        return tlbRepository.delete(id).thenApplyAsync(v -> {
            // This is the HTTP rendering thread context
            flash("success", "Tlb has been deleted: " + id.toString());
            return GO_HOME;
        }, httpExecutionContext.current());
    }
    
    /**
     * Handle json tlb upload 
     */
    @BodyParser.Of(BodyParser.Json.class)
    public Result uploadJson(){
        JsonNode json = request().body().asJson();
        String sJson = json.toString();
        ObjectMapper mapper = new ObjectMapper();
         
        logger.debug("called loadManyJson");
        
        try {
        	List<TLB> listTlb = mapper.readValue(sJson, new TypeReference<List<TLB>>(){});
			logger.debug("called loadManyJson: end readValue: listTlb as list size: " + listTlb.size());
			for (TLB tlb : listTlb){
				tlbRepository.insert(tlb);
			}
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			logger.debug("uploadJson: JsonParseException: " + e.toString());
			e.printStackTrace();
			return badRequest("uploadJson: JsonParseException Parsing json :" + e.toString());
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return badRequest("uploadJson: JsonMappingException Parsing json :" + e.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return badRequest("uploadJson: IOException Parsing json :" + e.toString());
		} finally{
			return ok("json records added");
		}
    }
}


