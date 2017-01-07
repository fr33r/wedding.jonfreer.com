package com.jonfreer.wedding.infrastructure.repositories;

import com.jonfreer.wedding.domain.interfaces.repositories.IResourceMetadataRepository;
import com.jonfreer.wedding.domain.interfaces.unitofwork.IDatabaseUnitOfWork;
import com.jonfreer.wedding.domain.metadata.ResourceMetadata;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.CallableStatement;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author jonfreer
 * @since 1/4/17
 */
public class ResourceMetadataRepository extends DatabaseRepository implements IResourceMetadataRepository {

    /**
     * Constructs a repository of ResourceMetadata instances.
     * @param unitOfWork An instance of a class that implements the
     *                   IDatabaseUnitOfWork interface. All methods invoked
     *                   on the ResourceMetadataRepository instance being created will
     *                   utilize this unit of work.
     */
    public ResourceMetadataRepository(IDatabaseUnitOfWork unitOfWork){
        super(unitOfWork);
    }

    /**
     * Retrieves resource metadata for a resource identified by
     * the provided URI.
     *
     * @param uri The URI of the resource to retrieve metadata for.
     * @return The resource metadata for the resource identified by
     * the provided URI.
     */
    @Override
    public ResourceMetadata getResourceMetadata(URI uri) {
        CallableStatement cStatement =
            this.getUnitOfWork().createCallableStatement("{ CALL GetResourceMetadata(?) }");
        ResultSet results = null;
        try {
            cStatement.setString(1, uri.toString());
            results = cStatement.executeQuery();
            if(results.next()){
                ResourceMetadata resourceMetadata;
                resourceMetadata = new ResourceMetadata(
                        new URI(results.getString(1)),
                        results.getDate(2),
                        results.getString(3)
                );
                return resourceMetadata;
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();

        } catch (URISyntaxException uriSyntaxException) {
            uriSyntaxException.printStackTrace();
        }
        finally{
            try{
                if(cStatement != null && !cStatement.isClosed()){
                    cStatement.close();
                }
                if(results != null && !results.isClosed()){
                    results.close();
                }
            }
            catch(SQLException anotherSqlException){
                anotherSqlException.printStackTrace();
            }
        }
        return null;
    }

    /**
     * Creates a new representation of resource metadata with the
     * provided state.
     *
     * @param resourceMetadata The desired state for the new resource metadata.
     */
    @Override
    public void insertResourceMetadata(ResourceMetadata resourceMetadata) {
        CallableStatement cStatement =
            this.getUnitOfWork().createCallableStatement("{ CALL CreateResourceMetadata(?, ?, ?) }");
        try {
            cStatement.setString(1, resourceMetadata.getUri().toString());
            cStatement.setDate(2, new Date(resourceMetadata.getLastModified().getTime()));
            cStatement.setString(3, resourceMetadata.getEntityTag());
            cStatement.executeUpdate();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } finally {
            try{
                if(cStatement != null && !cStatement.isClosed()){
                    cStatement.close();
                }
            }
            catch(SQLException anotherSqlException){
                anotherSqlException.printStackTrace();
            }
        }
    }

    /**
     * Replaces the state an existing representation of metadata about a resource
     * with the provided state.
     *
     * @param resourceMetadata The desired state for the resource metadata.
     */
    @Override
    public void updateResourceMetaData(ResourceMetadata resourceMetadata) {
        CallableStatement cStatement =
            this.getUnitOfWork().createCallableStatement("{ CALL UpdateResourceData(?, ?, ?) }");
        try {
            cStatement.setString(1, resourceMetadata.getUri().toString());
            cStatement.setDate(2, new Date(resourceMetadata.getLastModified().getTime()));
            cStatement.setString(3, resourceMetadata.getEntityTag());
            cStatement.executeUpdate();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } finally {
            try{
                if(cStatement != null && !cStatement.isClosed()){
                    cStatement.close();
                }
            }
            catch(SQLException anotherSqlException){
                anotherSqlException.printStackTrace();
            }
        }
    }

    /**
     * Deletes the resource metadata for a resource.
     *
     * @param uri The URI of the resource to delete metadata for.
     */
    @Override
    public void deleteResourceMetaData(URI uri) {
        CallableStatement cStatement =
            this.getUnitOfWork().createCallableStatement("{ CALL DeleteResourceMetadata(?) }");
        try{

            cStatement.setString(1, uri.toString());
            cStatement.executeUpdate();
        }catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } finally {
            try{
                if(cStatement != null && !cStatement.isClosed()){
                    cStatement.close();
                }
            }catch(SQLException anotherSqlException) {
                anotherSqlException.printStackTrace();
            }
        }
    }
}
