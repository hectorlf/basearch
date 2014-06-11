package basearch.model.meta;

import basearch.model.Language;
import basearch.model.User;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2014-06-11T20:45:47")
@StaticMetamodel(User.class)
public class User_ extends PersistentObject_ {

    public static volatile SingularAttribute<User, Boolean> enabled;
    public static volatile SingularAttribute<User, String> username;
    public static volatile SingularAttribute<User, Language> language;

}