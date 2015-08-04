package com.krillsson.sysapi.resources;

import io.dropwizard.auth.Auth;
import com.krillsson.sysapi.representation.config.UserConfiguration;
import com.krillsson.sysapi.representation.filesystem.FSType;
import com.krillsson.sysapi.representation.filesystem.FileSystem;
import com.krillsson.sysapi.sigar.FilesystemSigar;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("filesystems")
@Produces(MediaType.APPLICATION_JSON)
public class FilesystemResource extends Resource {
    FilesystemSigar filesystemSigar;

    public FilesystemResource(FilesystemSigar filesystemSigar) {
        this.filesystemSigar = filesystemSigar;
    }

    @GET
    @Override
    public List<FileSystem> getRoot(@Auth UserConfiguration user) {
        return filesystemSigar.getFilesystems();
    }

    @GET
    @Path("type/{fsTypeName}")
    public List<FileSystem> getFsByType(@Auth UserConfiguration user, @PathParam("fsTypeName") String fsTypeName) {
        FSType fsType;
        List<FileSystem> fileSystems;
        try {
            fsType = FSType.valueOf(fsTypeName);
            fileSystems = filesystemSigar.getFileSystemsWithCategory(fsType);
        } catch (IllegalArgumentException e) {
            throw buildWebException(Response.Status.NOT_FOUND, e.getMessage());
        }
        return fileSystems;
    }

    @GET
    @Path("{id}")
    public FileSystem getFsById(@Auth UserConfiguration user, @PathParam("id") String id) {
        FileSystem fileSystem;
        try {
            fileSystem = filesystemSigar.getFileSystemById(id);
        } catch (IllegalArgumentException e) {
            throw buildWebException(Response.Status.NOT_FOUND, e.getMessage());
        }
        return fileSystem;
    }

}
