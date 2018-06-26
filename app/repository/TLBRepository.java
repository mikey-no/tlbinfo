package repository;

import io.ebean.*;
import models.TLB;
import play.db.ebean.EbeanConfig;

import javax.inject.Inject;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletionStage;

import static java.util.concurrent.CompletableFuture.supplyAsync;

/**
 * A repository that executes database operations in a different
 * execution context.
 */
public class TLBRepository {

    private final EbeanServer ebeanServer;
    private final DatabaseExecutionContext executionContext;

    @Inject
    public TLBRepository(EbeanConfig ebeanConfig, DatabaseExecutionContext executionContext) {
        this.ebeanServer = Ebean.getServer(ebeanConfig.defaultServer());
        this.executionContext = executionContext;
    }

    /**
     * Return a paged list of TLBs
     *
     * @param page     Page to display
     * @param pageSize Number of tlbs per page
     * @param sortBy   TLB property used for sorting
     * @param order    Sort order (either or asc or desc)
     * @param filter   Filter applied on the name column
     */
    public CompletionStage<PagedList<TLB>> page(int page, int pageSize, String sortBy, String order, String filter) {
        return supplyAsync(() ->
                ebeanServer.find(TLB.class).where()
                    .ilike("name", "%" + filter + "%")
                    .orderBy(sortBy + " " + order)
                    //.fetch("company")
                    .setFirstRow(page * pageSize)
                    .setMaxRows(pageSize)
                    .findPagedList(), executionContext);
    }
    
    /**
     * Return a list of tlb(s), in separate thread asynchronously
     */
    public CompletionStage<List<TLB>> findAll() {
        return supplyAsync(() ->
                ebeanServer.find(TLB.class).findList(), executionContext);
    }
    
    /**
     * Return a list of tlb(s), and wait for the reply (blocking)
     */
    public List<TLB> findAllNow() {
        return ebeanServer.find(TLB.class).findList();
    }

    public CompletionStage<Optional<TLB>> lookup(Long id) {
        return supplyAsync(() -> Optional.ofNullable(ebeanServer.find(TLB.class).setId(id).findOne()), executionContext);
    }

    public CompletionStage<Optional<Long>> update(Long id, TLB newTLBData) {
        return supplyAsync(() -> {
            Transaction txn = ebeanServer.beginTransaction();
            Optional<Long> value = Optional.empty();
            try {
                TLB savedTLB = ebeanServer.find(TLB.class).setId(id).findOne();
                if (savedTLB != null) {
                    savedTLB.discontinued = newTLBData.discontinued;
                    savedTLB.introduced = newTLBData.introduced;
                    savedTLB.name = newTLBData.name;
                    savedTLB.modified = new Date(); //put todays date on the modified record
                    savedTLB.tlbid = newTLBData.tlbid;
                    savedTLB.note = newTLBData.note;
                    savedTLB.update();
                    txn.commit();
                    value = Optional.of(id);
                }
            } finally {
                txn.end();
            }
            return value;
        }, executionContext);
    }

    public CompletionStage<Optional<Long>>  delete(Long id) {
        return supplyAsync(() -> {
            try {
                final Optional<TLB> tlbOptional = Optional.ofNullable(ebeanServer.find(TLB.class).setId(id).findOne());
                tlbOptional.ifPresent(Model::delete);
                return tlbOptional.map(c -> c.id);
            } catch (Exception e) {
                return Optional.empty();
            }
        }, executionContext);
    }

    public CompletionStage<Long> insert(TLB tlb) {
        return supplyAsync(() -> {
             //tlb.id = System.currentTimeMillis(); // not ideal, but it works
             tlb.created = new Date(); //put todays date on the newly created record
             ebeanServer.insert(tlb);
             return tlb.id;
        }, executionContext);
    }
    


}
