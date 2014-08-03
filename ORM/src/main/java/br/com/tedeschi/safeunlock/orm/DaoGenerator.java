package br.com.tedeschi.safeunlock.orm;

import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class DaoGenerator {

    /** VO sub-package constant */
    public static final String VO_SUBPACKAGE = ".persistence.vo";

    /** DAO sub-package constant */
    public static final String DAO_SUBPACKAGE = ".persistence.dao";

    /** persistence package */
    protected static final String PACKAGE = "br.com.tedeschi.safeunlock";

    /** project source path */
    protected static final String PROJECT_SRC = "../app/src/main/java";

    /** database version */
    private static final int DB_VERSION = 1;

    public static void main(String args[]) throws Exception {
        Schema schema = new Schema(DB_VERSION, PACKAGE + VO_SUBPACKAGE);
        schema.setDefaultJavaPackageDao(PACKAGE + DAO_SUBPACKAGE);

        Entity settings = schema.addEntity("Settings");
        settings.setTableName("TB_SETTINGS");
        settings.setHasKeepSections(true);
        settings.addLongProperty("id").columnName("id").primaryKey().autoincrement();
        settings.addBooleanProperty("enabled").columnName("enabled").columnType("boolean");

        Entity connection = schema.addEntity("Connection");
        connection.setTableName("TB_CONNECTION");
        connection.setHasKeepSections(true);
        connection.addLongProperty("id").columnName("id").primaryKey().autoincrement();
        connection.addStringProperty("name").columnName("name").notNull();
        connection.addStringProperty("uniqueId").columnName("uniqueId").notNull();
        connection.addIntProperty("type").columnName("type").notNull();
        connection.addBooleanProperty("checked").columnName("checked").columnType("boolean");

        new de.greenrobot.daogenerator.DaoGenerator().generateAll(schema, PROJECT_SRC);
    }
}
