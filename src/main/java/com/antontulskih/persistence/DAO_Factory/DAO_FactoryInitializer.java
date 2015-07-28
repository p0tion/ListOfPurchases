/**
* @{NAME}
*
* ${DATE}
*
* @author Tulskih Anton
*/

package com.antontulskih.persistence.DAO_Factory;

import com.antontulskih.persistence.DAO_Factory.Implementation.*;

public abstract class DAO_FactoryInitializer {

    public static DAO_AbstractFactory getDAO_Factory(final StorageType st) {
        switch(st) {
            case COLLECTION:
                return new CollectionDAO_Factory();
            case JSON:
                return new JSON_DAO_Factory();
            case JDBC:
                return new JDBC_DAO_Factory();
            case XML:
                return new XML_DAO_Factory();
            case SERIALIZATION:
                return new SerializationDAO_Factory();
            case HIBERNATE:
                return new HibernateDAO_Factory();
            default:
                throw new IllegalArgumentException("Parameter must be either "
                        + StorageType.COLLECTION + ", "
                        + StorageType.JSON + ", "
                        + StorageType.JDBC + ", "
                        + StorageType.HIBERNATE + ", "
                        + StorageType.XML + " or "
                        + StorageType.SERIALIZATION + ".");
        }
    }
}
