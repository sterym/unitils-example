package com.example.util;

import java.lang.reflect.Method;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Map;
import java.util.Set;

import org.hibernate.LockMode;
import org.hibernate.dialect.Dialect;
import org.hibernate.dialect.lock.LockingStrategy;
import org.hibernate.exception.SQLExceptionConverter;
import org.hibernate.exception.ViolatedConstraintNameExtracter;
import org.hibernate.persister.entity.Lockable;
import org.hibernate.sql.CaseFragment;
import org.hibernate.sql.JoinFragment;

/**
 * A specialized dialect for the test-creation. This dialect uses a real dialect to delegate most of the calls to and
 * manipulates there where needed so there are no constraints (except for 'Not Null') in the database and strings are
 * mapped to char.
 *
 */
public class DialectForTests extends Dialect {

    private final Dialect realDialect;

    /**
     * Instantiates a new dialect for tests.
     * 
     * @param realDialect the real dialect
     */
    public DialectForTests(final Dialect realDialect) {
        super();
        this.realDialect = realDialect;
        Method m;
        try {
            m = Dialect.class.getDeclaredMethod("registerColumnType", Integer.TYPE, String.class);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
        m.setAccessible(true);
        try {
            m.invoke(realDialect, 12, "char($l)");
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String appendIdentitySelectToInsert(final String insertString) {
        return realDialect.appendIdentitySelectToInsert(insertString);
    }

    /**
     * Append lock hint.
     * 
     * @param mode the mode
     * @param tableName the table name
     * 
     * @return the string
     * 
     * @see org.hibernate.dialect.Dialect#appendLockHint(org.hibernate.LockMode, java.lang.String)
     */
    @Override
    public String appendLockHint(final LockMode mode, final String tableName) {
        return realDialect.appendLockHint(mode, tableName);
    }

    /**
     * Apply locks to sql.
     * 
     * @param sql the sql
     * @param aliasedLockModes the aliased lock modes
     * @param keyColumnNames the key column names
     * 
     * @return the string
     * 
     * @see org.hibernate.dialect.Dialect#applyLocksToSql(java.lang.String, java.util.Map, java.util.Map)
     */
    @Override
    public String applyLocksToSql(final String sql, final Map aliasedLockModes, final Map keyColumnNames) {
        return realDialect.applyLocksToSql(sql, aliasedLockModes, keyColumnNames);
    }

    /**
     * Are string comparisons case insensitive.
     * 
     * @return true, if are string comparisons case insensitive
     * 
     * @see org.hibernate.dialect.Dialect#areStringComparisonsCaseInsensitive()
     */
    @Override
    public boolean areStringComparisonsCaseInsensitive() {
        return realDialect.areStringComparisonsCaseInsensitive();
    }

    /**
     * Bind limit parameters first.
     * 
     * @return true, if bind limit parameters first
     * 
     * @see org.hibernate.dialect.Dialect#bindLimitParametersFirst()
     */
    @Override
    public boolean bindLimitParametersFirst() {
        return realDialect.bindLimitParametersFirst();
    }

    /**
     * Bind limit parameters in reverse order.
     * 
     * @return true, if bind limit parameters in reverse order
     * 
     * @see org.hibernate.dialect.Dialect#bindLimitParametersInReverseOrder()
     */
    @Override
    public boolean bindLimitParametersInReverseOrder() {
        return realDialect.bindLimitParametersInReverseOrder();
    }

    /**
     * Builds the sql exception converter.
     * 
     * @return the SQL exception converter
     * 
     * @see org.hibernate.dialect.Dialect#buildSQLExceptionConverter()
     */
    @Override
    public SQLExceptionConverter buildSQLExceptionConverter() {
        return realDialect.buildSQLExceptionConverter();
    }

    /**
     * Close quote.
     * 
     * @return the char
     * 
     * @see org.hibernate.dialect.Dialect#closeQuote()
     */
    @Override
    public char closeQuote() {
        return realDialect.closeQuote();
    }

    /**
     * Creates the case fragment.
     * 
     * @return the case fragment
     * 
     * @see org.hibernate.dialect.Dialect#createCaseFragment()
     */
    @Override
    public CaseFragment createCaseFragment() {
        return realDialect.createCaseFragment();
    }

    /**
     * Creates the outer join fragment.
     * 
     * @return the join fragment
     * 
     * @see org.hibernate.dialect.Dialect#createOuterJoinFragment()
     */
    @Override
    public JoinFragment createOuterJoinFragment() {
        return realDialect.createOuterJoinFragment();
    }

    /**
     * Does read committed cause writers to block readers.
     * 
     * @return true, if does read committed cause writers to block readers
     * 
     * @see org.hibernate.dialect.Dialect#doesReadCommittedCauseWritersToBlockReaders()
     */
    @Override
    public boolean doesReadCommittedCauseWritersToBlockReaders() {
        return realDialect.doesReadCommittedCauseWritersToBlockReaders();
    }

    /**
     * Does repeatable read cause readers to block writers.
     * 
     * @return true, if does repeatable read cause readers to block writers
     * 
     * @see org.hibernate.dialect.Dialect#doesRepeatableReadCauseReadersToBlockWriters()
     */
    @Override
    public boolean doesRepeatableReadCauseReadersToBlockWriters() {
        return realDialect.doesRepeatableReadCauseReadersToBlockWriters();
    }

    /**
     * Drop constraints.
     * 
     * @return true, if drop constraints
     * 
     * @see org.hibernate.dialect.Dialect#dropConstraints()
     */
    @Override
    public boolean dropConstraints() {
        return realDialect.dropConstraints();
    }

    /**
     * Drop temporary table after use.
     * 
     * @return true, if drop temporary table after use
     * 
     * @see org.hibernate.dialect.Dialect#dropTemporaryTableAfterUse()
     */
    @Override
    public boolean dropTemporaryTableAfterUse() {
        return realDialect.dropTemporaryTableAfterUse();
    }

    /**
     * Equals.
     * 
     * @param o the o
     * 
     * @return true, if equals
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(final Object o) {
        return realDialect.equals(o);
    }

    /**
     * For update of columns.
     * 
     * @return true, if for update of columns
     * 
     * @see org.hibernate.dialect.Dialect#forUpdateOfColumns()
     */
    @Override
    public boolean forUpdateOfColumns() {
        return realDialect.forUpdateOfColumns();
    }

    /**
     * Generate temporary table name.
     * 
     * @param baseTableName the base table name
     * 
     * @return the string
     * 
     * @see org.hibernate.dialect.Dialect#generateTemporaryTableName(java.lang.String)
     */
    @Override
    public String generateTemporaryTableName(final String baseTableName) {
        return realDialect.generateTemporaryTableName(baseTableName);
    }

    /**
     * Gets the add column string.
     * 
     * @return the adds the column string
     * 
     * @see org.hibernate.dialect.Dialect#getAddColumnString()
     */
    @Override
    public String getAddColumnString() {
        return realDialect.getAddColumnString();
    }

    /**
     * Gets the add foreign key constraint string.
     * 
     * @param constraintName the constraint name
     * @param foreignKey the foreign key
     * @param referencedTable the referenced table
     * @param primaryKey the primary key
     * @param referencesPrimaryKey the references primary key
     * 
     * @return the adds the foreign key constraint string
     * 
     * @see org.hibernate.dialect.Dialect#getAddForeignKeyConstraintString(java.lang.String, java.lang.String[],
     * java.lang.String, java.lang.String[], boolean)
     */
    @Override
    public String getAddForeignKeyConstraintString(final String constraintName, final String[] foreignKey,
        final String referencedTable, final String[] primaryKey, final boolean referencesPrimaryKey) {
        return realDialect.getAddForeignKeyConstraintString(constraintName, foreignKey, referencedTable, primaryKey,
            referencesPrimaryKey);
    }

    /**
     * Gets the add primary key constraint string.
     * 
     * @param constraintName the constraint name
     * 
     * @return the adds the primary key constraint string
     * 
     * @see org.hibernate.dialect.Dialect#getAddPrimaryKeyConstraintString(java.lang.String)
     */
    @Override
    public String getAddPrimaryKeyConstraintString(final String constraintName) {
        return realDialect.getAddPrimaryKeyConstraintString(constraintName);
    }

    /**
     * Gets the cascade constraints string.
     * 
     * @return the cascade constraints string
     * 
     * @see org.hibernate.dialect.Dialect#getCascadeConstraintsString()
     */
    @Override
    public String getCascadeConstraintsString() {
        return realDialect.getCascadeConstraintsString();
    }

    /**
     * Gets the cast type name.
     * 
     * @param code the code
     * 
     * @return the cast type name
     * 
     * @see org.hibernate.dialect.Dialect#getCastTypeName(int)
     */
    @Override
    public String getCastTypeName(final int code) {
        return realDialect.getCastTypeName(code);
    }

    /**
     * Gets the column comment.
     * 
     * @param comment the comment
     * 
     * @return the column comment
     * 
     * @see org.hibernate.dialect.Dialect#getColumnComment(java.lang.String)
     */
    @Override
    public String getColumnComment(final String comment) {
        return realDialect.getColumnComment(comment);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public String[] getCreateSequenceStrings(final String sequenceName, final int initialValue,
        final int incrementSize) {
        return realDialect.getCreateSequenceStrings(sequenceName, initialValue, incrementSize);
    }

    /**
     * Gets the create sequence strings.
     * 
     * @param sequenceName the sequence name
     * 
     * @return the creates the sequence strings
     * 
     * @deprecated
     * @see org.hibernate.dialect.Dialect#getCreateSequenceStrings(java.lang.String)
     */
    @Deprecated
    @Override
    public String[] getCreateSequenceStrings(final String sequenceName) {
        return realDialect.getCreateSequenceStrings(sequenceName);
    }

    /**
     * Gets the create table string.
     * 
     * @return the creates the table string
     * 
     * @see org.hibernate.dialect.Dialect#getCreateTableString()
     */
    @Override
    public String getCreateTableString() {
        return "create memory table";
    }

    /**
     * Gets the create temporary table postfix.
     * 
     * @return the creates the temporary table postfix
     * 
     * @see org.hibernate.dialect.Dialect#getCreateTemporaryTablePostfix()
     */
    @Override
    public String getCreateTemporaryTablePostfix() {
        return realDialect.getCreateTemporaryTablePostfix();
    }

    /**
     * Gets the create temporary table string.
     * 
     * @return the creates the temporary table string
     * 
     * @see org.hibernate.dialect.Dialect#getCreateTemporaryTableString()
     */
    @Override
    public String getCreateTemporaryTableString() {
        return realDialect.getCreateTemporaryTableString();
    }

    /**
     * Gets the current timestamp select string.
     * 
     * @return the current timestamp select string
     * 
     * @see org.hibernate.dialect.Dialect#getCurrentTimestampSelectString()
     */
    @Override
    public String getCurrentTimestampSelectString() {
        return realDialect.getCurrentTimestampSelectString();
    }

    /**
     * Gets the current timestamp sql function name.
     * 
     * @return the current timestamp sql function name
     * 
     * @see org.hibernate.dialect.Dialect#getCurrentTimestampSQLFunctionName()
     */
    @Override
    public String getCurrentTimestampSQLFunctionName() {
        return realDialect.getCurrentTimestampSQLFunctionName();
    }

    /**
     * Gets the drop foreign key string.
     * 
     * @return the drop foreign key string
     * 
     * @see org.hibernate.dialect.Dialect#getDropForeignKeyString()
     */
    @Override
    public String getDropForeignKeyString() {
        return realDialect.getDropForeignKeyString();
    }

    /**
     * Gets the drop sequence strings.
     * 
     * @param sequenceName the sequence name
     * 
     * @return the drop sequence strings
     * 
     * @see org.hibernate.dialect.Dialect#getDropSequenceStrings(java.lang.String)
     */
    @Override
    public String[] getDropSequenceStrings(final String sequenceName) {
        return realDialect.getDropSequenceStrings(sequenceName);
    }

    /**
     * Gets the for update nowait string.
     * 
     * @return the for update nowait string
     * 
     * @see org.hibernate.dialect.Dialect#getForUpdateNowaitString()
     */
    @Override
    public String getForUpdateNowaitString() {
        return realDialect.getForUpdateNowaitString();
    }

    /**
     * Gets the for update nowait string.
     * 
     * @param aliases the aliases
     * 
     * @return the for update nowait string
     * 
     * @see org.hibernate.dialect.Dialect#getForUpdateNowaitString(java.lang.String)
     */
    @Override
    public String getForUpdateNowaitString(final String aliases) {
        return realDialect.getForUpdateNowaitString(aliases);
    }

    /**
     * Gets the for update string.
     * 
     * @return the for update string
     * 
     * @see org.hibernate.dialect.Dialect#getForUpdateString()
     */
    @Override
    public String getForUpdateString() {
        return realDialect.getForUpdateString();
    }

    /**
     * Gets the for update string.
     * 
     * @param lockMode the lock mode
     * 
     * @return the for update string
     * 
     * @see org.hibernate.dialect.Dialect#getForUpdateString(org.hibernate.LockMode)
     */
    @Override
    public String getForUpdateString(final LockMode lockMode) {
        return realDialect.getForUpdateString(lockMode);
    }

    /**
     * Gets the for update string.
     * 
     * @param aliases the aliases
     * 
     * @return the for update string
     * 
     * @see org.hibernate.dialect.Dialect#getForUpdateString(java.lang.String)
     */
    @Override
    public String getForUpdateString(final String aliases) {
        return realDialect.getForUpdateString(aliases);
    }

    /**
     * Gets the hibernate type name.
     * 
     * @param code the code
     * @param length the length
     * @param precision the precision
     * @param scale the scale
     * 
     * @return the hibernate type name
     * 
     * @see org.hibernate.dialect.Dialect#getHibernateTypeName(int, int, int, int)
     */
    @Override
    public String getHibernateTypeName(final int code, final int length, final int precision, final int scale){
        return realDialect.getHibernateTypeName(code, length, precision, scale);
    }

    /**
     * Gets the hibernate type name.
     * 
     * @param code the code
     * 
     * @return the hibernate type name
     * 
     * @see org.hibernate.dialect.Dialect#getHibernateTypeName(int)
     */
    @Override
    public String getHibernateTypeName(final int code) {
        return realDialect.getHibernateTypeName(code);
    }

    /**
     * Gets the identity column string.
     * 
     * @param type the type
     * 
     * @return the identity column string
     * 
     * @see org.hibernate.dialect.Dialect#getIdentityColumnString(int)
     */
    @Override
    public String getIdentityColumnString(final int type) {
        switch(type) {
            case Types.BIGINT:
            case Types.DECIMAL:
            case Types.DOUBLE:
            case Types.FLOAT:
            case Types.INTEGER:
            case Types.NUMERIC:
            case Types.REAL:
            case Types.SMALLINT:
            case Types.TINYINT:
                return "identity";
            default:
                return "";
        }
    }

    /**
     * Gets the identity insert string.
     * 
     * @return the identity insert string
     * 
     * @see org.hibernate.dialect.Dialect#getIdentityInsertString()
     */
    @Override
    public String getIdentityInsertString() {
        return realDialect.getIdentityInsertString();
    }

    /**
     * Gets the identity select string.
     * 
     * @param table the table
     * @param column the column
     * @param type the type
     * 
     * @return the identity select string
     * 
     * @see org.hibernate.dialect.Dialect#getIdentitySelectString(java.lang.String, java.lang.String, int)
     */
    @Override
    public String getIdentitySelectString(final String table, final String column, final int type){
        return realDialect.getIdentitySelectString(table, column, type);
    }

    /**
     * Gets the keywords.
     * 
     * @return the keywords
     * 
     * @see org.hibernate.dialect.Dialect#getKeywords()
     */
    @Override
    public Set getKeywords() {
        return realDialect.getKeywords();
    }

    /**
     * Gets the limit string.
     * 
     * @param query the query
     * @param offset the offset
     * @param limit the limit
     * 
     * @return the limit string
     * 
     * @see org.hibernate.dialect.Dialect#getLimitString(java.lang.String, int, int)
     */
    @Override
    public String getLimitString(final String query, final int offset, final int limit) {
        return realDialect.getLimitString(query, offset, limit);
    }

    /**
     * Gets the locking strategy.
     * 
     * @param lockable the lockable
     * @param lockMode the lock mode
     * 
     * @return the locking strategy
     * 
     * @see org.hibernate.dialect.Dialect#getLockingStrategy(org.hibernate.persister.entity.Lockable,
     * org.hibernate.LockMode)
     */
    @Override
    public LockingStrategy getLockingStrategy(final Lockable lockable, final LockMode lockMode) {
        return realDialect.getLockingStrategy(lockable, lockMode);
    }

    /**
     * Gets the lowercase function.
     * 
     * @return the lowercase function
     * 
     * @see org.hibernate.dialect.Dialect#getLowercaseFunction()
     */
    @Override
    public String getLowercaseFunction() {
        return realDialect.getLowercaseFunction();
    }

    /**
     * Gets the max alias length.
     * 
     * @return the max alias length
     * 
     * @see org.hibernate.dialect.Dialect#getMaxAliasLength()
     */
    @Override
    public int getMaxAliasLength() {
        return realDialect.getMaxAliasLength();
    }

    /**
     * Gets the native identifier generator class.
     * 
     * @return the native identifier generator class
     * 
     * @see org.hibernate.dialect.Dialect#getNativeIdentifierGeneratorClass()
     */
    @Override
    public Class getNativeIdentifierGeneratorClass() {
        return realDialect.getNativeIdentifierGeneratorClass();
    }

    /**
     * Gets the no columns insert string.
     * 
     * @return the no columns insert string
     * 
     * @see org.hibernate.dialect.Dialect#getNoColumnsInsertString()
     */
    @Override
    public String getNoColumnsInsertString() {
        return realDialect.getNoColumnsInsertString();
    }

    /**
     * Gets the null column string.
     * 
     * @return the null column string
     * 
     * @see org.hibernate.dialect.Dialect#getNullColumnString()
     */
    @Override
    public String getNullColumnString() {
        return realDialect.getNullColumnString();
    }

    /**
     * Gets the query sequences string.
     * 
     * @return the query sequences string
     * 
     * @see org.hibernate.dialect.Dialect#getQuerySequencesString()
     */
    @Override
    public String getQuerySequencesString() {
        return realDialect.getQuerySequencesString();
    }

    /**
     * Gets the result set.
     * 
     * @param statement the statement
     * 
     * @return the result set
     * 
     * @throws SQLException the SQL exception
     * 
     * @see org.hibernate.dialect.Dialect#getResultSet(java.sql.CallableStatement)
     */
    @Override
    public ResultSet getResultSet(final CallableStatement statement) throws SQLException {
        return realDialect.getResultSet(statement);
    }

    /**
     * Gets the select clause null string.
     * 
     * @param sqlType the sql type
     * 
     * @return the select clause null string
     * 
     * @see org.hibernate.dialect.Dialect#getSelectClauseNullString(int)
     */
    @Override
    public String getSelectClauseNullString(final int sqlType) {
        return realDialect.getSelectClauseNullString(sqlType);
    }

    /**
     * Gets the select guid string.
     * 
     * @return the select guid string
     * 
     * @see org.hibernate.dialect.Dialect#getSelectGUIDString()
     */
    @Override
    public String getSelectGUIDString() {
        return realDialect.getSelectGUIDString();
    }

    /**
     * Gets the select sequence next val string.
     * 
     * @param sequenceName the sequence name
     * 
     * @return the select sequence next val string
     * 
     * @see org.hibernate.dialect.Dialect#getSelectSequenceNextValString(java.lang.String)
     */
    @Override
    public String getSelectSequenceNextValString(final String sequenceName) {
        return realDialect.getSelectSequenceNextValString(sequenceName);
    }

    /**
     * Gets the sequence next val string.
     * 
     * @param sequenceName the sequence name
     * 
     * @return the sequence next val string
     * 
     * @see org.hibernate.dialect.Dialect#getSequenceNextValString(java.lang.String)
     */
    @Override
    public String getSequenceNextValString(final String sequenceName) {
        return realDialect.getSequenceNextValString(sequenceName);
    }

    /**
     * Gets the table comment.
     * 
     * @param comment the comment
     * 
     * @return the table comment
     * 
     * @see org.hibernate.dialect.Dialect#getTableComment(java.lang.String)
     */
    @Override
    public String getTableComment(final String comment) {
        return realDialect.getTableComment(comment);
    }

    /**
     * Gets the table type string.
     * 
     * @return the table type string
     * 
     * @see org.hibernate.dialect.Dialect#getTableTypeString()
     */
    @Override
    public String getTableTypeString() {
        return realDialect.getTableTypeString();
    }

    /**
     * Gets the type name.
     * 
     * @param code the code
     * @param length the length
     * @param precision the precision
     * @param scale the scale
     * 
     * @return the type name
     * 
     * @see org.hibernate.dialect.Dialect#getTypeName(int, int, int, int)
     */
    @Override
    public String getTypeName(final int code, final int length, final int precision, final int scale){
        return realDialect.getTypeName(code, length, precision, scale);
    }

    /**
     * Gets the type name.
     * 
     * @param code the code
     * 
     * @return the type name
     * 
     * @see org.hibernate.dialect.Dialect#getTypeName(int)
     */
    @Override
    public String getTypeName(final int code) {
        return realDialect.getTypeName(code);
    }

    /**
     * Gets the violated constraint name extracter.
     * 
     * @return the violated constraint name extracter
     * 
     * @see org.hibernate.dialect.Dialect#getViolatedConstraintNameExtracter()
     */
    @Override
    public ViolatedConstraintNameExtracter getViolatedConstraintNameExtracter() {
        return realDialect.getViolatedConstraintNameExtracter();
    }

    /**
     * Checks for alter table.
     * 
     * @return true, if checks for alter table
     * 
     * @see org.hibernate.dialect.Dialect#hasAlterTable()
     */
    @Override
    public boolean hasAlterTable() {
        return false;
        // return realDialect.hasAlterTable();
    }

    /**
     * Checks for data type in identity column.
     * 
     * @return true, if checks for data type in identity column
     * 
     * @see org.hibernate.dialect.Dialect#hasDataTypeInIdentityColumn()
     */
    @Override
    public boolean hasDataTypeInIdentityColumn() {
        return realDialect.hasDataTypeInIdentityColumn();
    }

    /**
     * Hash code.
     * 
     * @return the int
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return realDialect.hashCode();
    }

    /**
     * Checks for self referential foreign key bug.
     * 
     * @return true, if checks for self referential foreign key bug
     * 
     * @see org.hibernate.dialect.Dialect#hasSelfReferentialForeignKeyBug()
     */
    @Override
    public boolean hasSelfReferentialForeignKeyBug() {
        return realDialect.hasSelfReferentialForeignKeyBug();
    }

    /**
     * Checks if is current timestamp select string callable.
     * 
     * @return true, if checks if is current timestamp select string callable
     * 
     * @see org.hibernate.dialect.Dialect#isCurrentTimestampSelectStringCallable()
     */
    @Override
    public boolean isCurrentTimestampSelectStringCallable() {
        return realDialect.isCurrentTimestampSelectStringCallable();
    }

    /**
     * Open quote.
     * 
     * @return the char
     * 
     * @see org.hibernate.dialect.Dialect#openQuote()
     */
    @Override
    public char openQuote() {
        return realDialect.openQuote();
    }

    /**
     * Perform temporary table ddl in isolation.
     * 
     * @return true, if perform temporary table ddl in isolation
     * 
     * @see org.hibernate.dialect.Dialect#performTemporaryTableDDLInIsolation()
     */
    @Override
    public Boolean performTemporaryTableDDLInIsolation() {
        return realDialect.performTemporaryTableDDLInIsolation();
    }

    /**
     * Qualify index name.
     * 
     * @return true, if qualify index name
     * 
     * @see org.hibernate.dialect.Dialect#qualifyIndexName()
     */
    @Override
    public boolean qualifyIndexName() {
        return realDialect.qualifyIndexName();
    }

    /**
     * Register result set out parameter.
     * 
     * @param statement the statement
     * @param position the position
     * 
     * @return the int
     * 
     * @throws SQLException the SQL exception
     * 
     * @see org.hibernate.dialect.Dialect#registerResultSetOutParameter(java.sql.CallableStatement, int)
     */
    @Override
    public int registerResultSetOutParameter(final CallableStatement statement, final int position)
        throws SQLException {
        return realDialect.registerResultSetOutParameter(statement, position);
    }

    /**
     * Supports bind as callable argument.
     * 
     * @return true, if supports bind as callable argument
     * 
     * @see org.hibernate.dialect.Dialect#supportsBindAsCallableArgument()
     */
    @Override
    public boolean supportsBindAsCallableArgument() {
        return realDialect.supportsBindAsCallableArgument();
    }

    /**
     * Supports cascade delete.
     * 
     * @return true, if supports cascade delete
     * 
     * @see org.hibernate.dialect.Dialect#supportsCascadeDelete()
     */
    @Override
    public boolean supportsCascadeDelete() {
        return realDialect.supportsCascadeDelete();
    }

    /**
     * Supports circular cascade delete constraints.
     * 
     * @return true, if supports circular cascade delete constraints
     * 
     * @see org.hibernate.dialect.Dialect#supportsCircularCascadeDeleteConstraints()
     */
    @Override
    public boolean supportsCircularCascadeDeleteConstraints() {
        return realDialect.supportsCircularCascadeDeleteConstraints();
    }

    /**
     * Supports column check.
     * 
     * @return true, if supports column check
     * 
     * @see org.hibernate.dialect.Dialect#supportsColumnCheck()
     */
    @Override
    public boolean supportsColumnCheck() {
        return realDialect.supportsColumnCheck();
    }

    /**
     * Supports comment on.
     * 
     * @return true, if supports comment on
     * 
     * @see org.hibernate.dialect.Dialect#supportsCommentOn()
     */
    @Override
    public boolean supportsCommentOn() {
        return realDialect.supportsCommentOn();
    }

    /**
     * Supports current timestamp selection.
     * 
     * @return true, if supports current timestamp selection
     * 
     * @see org.hibernate.dialect.Dialect#supportsCurrentTimestampSelection()
     */
    @Override
    public boolean supportsCurrentTimestampSelection() {
        return realDialect.supportsCurrentTimestampSelection();
    }

    /**
     * Supports empty in list.
     * 
     * @return true, if supports empty in list
     * 
     * @see org.hibernate.dialect.Dialect#supportsEmptyInList()
     */
    @Override
    public boolean supportsEmptyInList() {
        return realDialect.supportsEmptyInList();
    }

    /**
     * Supports exists in select.
     * 
     * @return true, if supports exists in select
     * 
     * @see org.hibernate.dialect.Dialect#supportsExistsInSelect()
     */
    @Override
    public boolean supportsExistsInSelect() {
        return realDialect.supportsExistsInSelect();
    }

    /**
     * Supports expected lob usage pattern.
     * 
     * @return true, if supports expected lob usage pattern
     * 
     * @see org.hibernate.dialect.Dialect#supportsExpectedLobUsagePattern()
     */
    @Override
    public boolean supportsExpectedLobUsagePattern() {
        return realDialect.supportsExpectedLobUsagePattern();
    }

    /**
     * Supports identity columns.
     * 
     * @return true, if supports identity columns
     * 
     * @see org.hibernate.dialect.Dialect#supportsIdentityColumns()
     */
    @Override
    public boolean supportsIdentityColumns() {
        return realDialect.supportsIdentityColumns();
    }

    /**
     * Supports if exists after table name.
     * 
     * @return true, if supports if exists after table name
     * 
     * @see org.hibernate.dialect.Dialect#supportsIfExistsAfterTableName()
     */
    @Override
    public boolean supportsIfExistsAfterTableName() {
        return realDialect.supportsIfExistsAfterTableName();
    }

    /**
     * Supports if exists before table name.
     * 
     * @return true, if supports if exists before table name
     * 
     * @see org.hibernate.dialect.Dialect#supportsIfExistsBeforeTableName()
     */
    @Override
    public boolean supportsIfExistsBeforeTableName() {
        return realDialect.supportsIfExistsBeforeTableName();
    }

    /**
     * Supports insert select identity.
     * 
     * @return true, if supports insert select identity
     * 
     * @see org.hibernate.dialect.Dialect#supportsInsertSelectIdentity()
     */
    @Override
    public boolean supportsInsertSelectIdentity() {
        return realDialect.supportsInsertSelectIdentity();
    }

    /**
     * Supports limit.
     * 
     * @return true, if supports limit
     * 
     * @see org.hibernate.dialect.Dialect#supportsLimit()
     */
    @Override
    public boolean supportsLimit() {
        return realDialect.supportsLimit();
    }

    /**
     * Supports limit offset.
     * 
     * @return true, if supports limit offset
     * 
     * @see org.hibernate.dialect.Dialect#supportsLimitOffset()
     */
    @Override
    public boolean supportsLimitOffset() {
        return realDialect.supportsLimitOffset();
    }

    /**
     * Supports lob value change propogation.
     * 
     * @return true, if supports lob value change propogation
     * 
     * @see org.hibernate.dialect.Dialect#supportsLobValueChangePropogation()
     */
    @Override
    public boolean supportsLobValueChangePropogation() {
        return realDialect.supportsLobValueChangePropogation();
    }

    /**
     * Supports not null unique.
     * 
     * @return true, if supports not null unique
     * 
     * @see org.hibernate.dialect.Dialect#supportsNotNullUnique()
     */
    @Override
    public boolean supportsNotNullUnique() {
        return realDialect.supportsNotNullUnique();
    }

    /**
     * Supports outer join for update.
     * 
     * @return true, if supports outer join for update
     * 
     * @see org.hibernate.dialect.Dialect#supportsOuterJoinForUpdate()
     */
    @Override
    public boolean supportsOuterJoinForUpdate() {
        return realDialect.supportsOuterJoinForUpdate();
    }

    /**
     * Supports parameters in insert select.
     * 
     * @return true, if supports parameters in insert select
     * 
     * @see org.hibernate.dialect.Dialect#supportsParametersInInsertSelect()
     */
    @Override
    public boolean supportsParametersInInsertSelect() {
        return realDialect.supportsParametersInInsertSelect();
    }

    /**
     * Supports pooled sequences.
     * 
     * @return true, if supports pooled sequences
     * 
     * @see org.hibernate.dialect.Dialect#supportsPooledSequences()
     */
    @Override
    public boolean supportsPooledSequences() {
        return realDialect.supportsPooledSequences();
    }

    /**
     * Supports result set position query methods on forward only cursor.
     * 
     * @return true, if supports result set position query methods on forward only cursor
     * 
     * @see org.hibernate.dialect.Dialect#supportsResultSetPositionQueryMethodsOnForwardOnlyCursor()
     */
    @Override
    public boolean supportsResultSetPositionQueryMethodsOnForwardOnlyCursor() {
        return realDialect.supportsResultSetPositionQueryMethodsOnForwardOnlyCursor();
    }

    /**
     * Supports row value constructor syntax.
     * 
     * @return true, if supports row value constructor syntax
     * 
     * @see org.hibernate.dialect.Dialect#supportsRowValueConstructorSyntax()
     */
    @Override
    public boolean supportsRowValueConstructorSyntax() {
        return realDialect.supportsRowValueConstructorSyntax();
    }

    /**
     * Supports row value constructor syntax in in list.
     * 
     * @return true, if supports row value constructor syntax in in list
     * 
     * @see org.hibernate.dialect.Dialect#supportsRowValueConstructorSyntaxInInList()
     */
    @Override
    public boolean supportsRowValueConstructorSyntaxInInList() {
        return realDialect.supportsRowValueConstructorSyntaxInInList();
    }

    /**
     * Supports sequences.
     * 
     * @return true, if supports sequences
     * 
     * @see org.hibernate.dialect.Dialect#supportsSequences()
     */
    @Override
    public boolean supportsSequences() {
        return realDialect.supportsSequences();
    }

    /**
     * Supports subquery on mutating table.
     * 
     * @return true, if supports subquery on mutating table
     * 
     * @see org.hibernate.dialect.Dialect#supportsSubqueryOnMutatingTable()
     */
    @Override
    public boolean supportsSubqueryOnMutatingTable() {
        return realDialect.supportsSubqueryOnMutatingTable();
    }

    /**
     * Supports subselect as in predicate lhs.
     * 
     * @return true, if supports subselect as in predicate lhs
     * 
     * @see org.hibernate.dialect.Dialect#supportsSubselectAsInPredicateLHS()
     */
    @Override
    public boolean supportsSubselectAsInPredicateLHS() {
        return realDialect.supportsSubselectAsInPredicateLHS();
    }

    /**
     * Supports table check.
     * 
     * @return true, if supports table check
     * 
     * @see org.hibernate.dialect.Dialect#supportsTableCheck()
     */
    @Override
    public boolean supportsTableCheck() {
        return realDialect.supportsTableCheck();
    }

    /**
     * Supports temporary tables.
     * 
     * @return true, if supports temporary tables
     * 
     * @see org.hibernate.dialect.Dialect#supportsTemporaryTables()
     */
    @Override
    public boolean supportsTemporaryTables() {
        return realDialect.supportsTemporaryTables();
    }

    /**
     * Supports unbounded lob locator materialization.
     * 
     * @return true, if supports unbounded lob locator materialization
     * 
     * @see org.hibernate.dialect.Dialect#supportsUnboundedLobLocatorMaterialization()
     */
    @Override
    public boolean supportsUnboundedLobLocatorMaterialization() {
        return realDialect.supportsUnboundedLobLocatorMaterialization();
    }

    /**
     * Supports union all.
     * 
     * @return true, if supports union all
     * 
     * @see org.hibernate.dialect.Dialect#supportsUnionAll()
     */
    @Override
    public boolean supportsUnionAll() {
        return realDialect.supportsUnionAll();
    }

    /**
     * Supports unique.
     * 
     * @return true, if supports unique
     * 
     * @see org.hibernate.dialect.Dialect#supportsUnique()
     */
    @Override
    public boolean supportsUnique() {
        return realDialect.supportsUnique();
    }

    /**
     * Supports unique constraint in create alter table.
     * 
     * @return true, if supports unique constraint in create alter table
     * 
     * @see org.hibernate.dialect.Dialect#supportsUniqueConstraintInCreateAlterTable()
     */
    @Override
    public boolean supportsUniqueConstraintInCreateAlterTable() {
        return realDialect.supportsUniqueConstraintInCreateAlterTable();
    }

    /**
     * Supports variable limit.
     * 
     * @return true, if supports variable limit
     * 
     * @see org.hibernate.dialect.Dialect#supportsVariableLimit()
     */
    @Override
    public boolean supportsVariableLimit() {
        return realDialect.supportsVariableLimit();
    }

    /**
     * To boolean value string.
     * 
     * @param bool the bool
     * 
     * @return the string
     * 
     * @see org.hibernate.dialect.Dialect#toBooleanValueString(boolean)
     */
    @Override
    public String toBooleanValueString(final boolean bool) {
        return realDialect.toBooleanValueString(bool);
    }

    /**
     * Transform select string.
     * 
     * @param select the select
     * 
     * @return the string
     * 
     * @see org.hibernate.dialect.Dialect#transformSelectString(java.lang.String)
     */
    @Override
    public String transformSelectString(final String select) {
        return realDialect.transformSelectString(select);
    }

    /**
     * Use input stream to insert blob.
     * 
     * @return true, if use input stream to insert blob
     * 
     * @see org.hibernate.dialect.Dialect#useInputStreamToInsertBlob()
     */
    @Override
    public boolean useInputStreamToInsertBlob() {
        return realDialect.useInputStreamToInsertBlob();
    }

    /**
     * Use max for limit.
     * 
     * @return true, if use max for limit
     * 
     * @see org.hibernate.dialect.Dialect#useMaxForLimit()
     */
    @Override
    public boolean useMaxForLimit() {
        return realDialect.useMaxForLimit();
    }

}
