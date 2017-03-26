/**
 * 
 */
package com.jonfreer.wedding.infrastructure.services;

import java.net.URI;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.TimeZone;

import javax.inject.Inject;
import javax.ws.rs.core.EntityTag;

import org.jvnet.hk2.annotations.Service;

import com.jonfreer.wedding.domain.interfaces.unitofwork.IDatabaseUnitOfWork;
import com.jonfreer.wedding.infrastructure.interfaces.factories.IDatabaseUnitOfWorkFactory;
import com.jonfreer.wedding.infrastructure.metadata.ResourceMetadata;

/**
 * An infrastructure service offering several operations to 
 * calling clients that wish to interact with REST resource metadata.
 * 
 * @author jonfreer
 * @since 1/4/17
 */
@Service
public class ResourceMetadataService
	implements com.jonfreer.wedding.infrastructure.interfaces.services.ResourceMetadataService {

	private final IDatabaseUnitOfWorkFactory unitOfWorkFactory;
	
	@Inject
	public ResourceMetadataService(IDatabaseUnitOfWorkFactory unitOfWorkFactory){
		this.unitOfWorkFactory = unitOfWorkFactory;
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
		
		IDatabaseUnitOfWork unitOfWork = this.unitOfWorkFactory.create();		
		CallableStatement cStatement =
            unitOfWork.createCallableStatement("{ CALL GetResourceMetadata(?) }");
        ResultSet results = null;
        
        try {
            cStatement.setString(1, uri.toString());
            results = cStatement.executeQuery();

            if(results.next()){
            	String matchingUri = results.getString(1);
            	Timestamp lastModified = results.getTimestamp(2, Calendar.getInstance(TimeZone.getTimeZone("UTC")));
            	String entityTag = results.getString(3);
            	entityTag = entityTag.replace("\"", "");
            	
            	unitOfWork.Save();
            	
            	ResourceMetadata resourceMetadata = 
                	new ResourceMetadata(URI.create(matchingUri), lastModified, new EntityTag(entityTag));
                return resourceMetadata;
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            unitOfWork.Undo();
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
		
		IDatabaseUnitOfWork unitOfWork = this.unitOfWorkFactory.create();	
		CallableStatement cStatement =
            unitOfWork.createCallableStatement("{ CALL CreateResourceMetadata(?, ?, ?) }");
		
        try {
            cStatement.setString(1, resourceMetadata.getUri().toString());
            cStatement.setTimestamp(
            		2, 
            		new Timestamp(resourceMetadata.getLastModified().getTime()), 
            		Calendar.getInstance(TimeZone.getTimeZone("UTC"))
    		);
            cStatement.setString(3, resourceMetadata.getEntityTag().toString());
            cStatement.executeUpdate();
            unitOfWork.Save();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            unitOfWork.Undo();
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
		
		IDatabaseUnitOfWork unitOfWork = this.unitOfWorkFactory.create();		
		CallableStatement cStatement =
            unitOfWork.createCallableStatement("{ CALL UpdateResourceMetadata(?, ?, ?) }");
		
        try {
            cStatement.setString(1, resourceMetadata.getUri().toString());
            cStatement.setTimestamp(
            		2, 
            		new Timestamp(resourceMetadata.getLastModified().getTime()), 
            		Calendar.getInstance(TimeZone.getTimeZone("UTC"))
    		);
            cStatement.setString(3, resourceMetadata.getEntityTag().toString());
            cStatement.executeUpdate();
            unitOfWork.Save();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            unitOfWork.Undo();
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
		
		IDatabaseUnitOfWork unitOfWork = this.unitOfWorkFactory.create();		
		CallableStatement cStatement =
            unitOfWork.createCallableStatement("{ CALL DeleteResourceMetadata(?) }");
		
        try{
            cStatement.setString(1, uri.toString());
            cStatement.executeUpdate();
            unitOfWork.Save();
        }catch (SQLException sqlException) {
            sqlException.printStackTrace();
            unitOfWork.Undo();
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
