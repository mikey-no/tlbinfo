package models;

import io.ebean.Model;
import play.data.format.Formats;

import java.util.Date;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class BaseModel extends Model {
   @Id
   public Long id;
   
   /**
    * Date record was added
    */
   @Formats.DateTime(pattern="yyyy-MM-dd HH:mm")
   public Date created;
   
   /**
    * Date record was modified
    */
   @Formats.DateTime(pattern="yyyy-MM-dd HH:mm")
   public Date modified;
}
