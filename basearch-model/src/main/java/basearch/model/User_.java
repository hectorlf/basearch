package basearch.model;

import basearch.model.Language;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2014-06-13T22:32:40")
@StaticMetamodel(User.class)
public class User_ extends PersistentObject_ {

    public static volatile SingularAttribute<User, Boolean> enabled;
    public static volatile SingularAttribute<User, String> username;
    public static volatile SingularAttribute<User, Language> language;

}