package models;

import play.data.format.Formats;
import play.data.validation.Constraints;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.util.Date;

/**
 * TLB Entity entity managed by Ebean
 */
@Entity 
public class TLB extends BaseModel {

    private static final long serialVersionUID = 1L;

    /*
     * TLB ID is the primary key for users of this data
     */
    @Constraints.Required
    public String tlbid;
    
    /*
     * Authoritative name of the TLB
     */
    @Constraints.Required
    public String name;
    
    /*
     * Date the TLB was created
     */
    @Formats.DateTime(pattern="yyyy-MM-dd")
    public Date introduced;
    
    /*
     * Date the TLB ceases to exist 
     */
    @Formats.DateTime(pattern="yyyy-MM-dd")
    public Date discontinued;
    
    /**
     * Address of the tlb
     */
    @Column(length=1024) 
    public String address;
       
        
    /**
     * Note to describe the tlb
     */
    @Column(length=2048) 
    public String note;
       
}

