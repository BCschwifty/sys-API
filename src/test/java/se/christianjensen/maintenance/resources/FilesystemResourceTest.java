package se.christianjensen.maintenance.resources;

import io.dropwizard.testing.junit.ResourceTestRule;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import se.christianjensen.maintenance.sigar.CpuMetrics;
import se.christianjensen.maintenance.sigar.FilesystemMetrics;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class FilesystemResourceTest {

    private static final FilesystemMetrics filesystemMock = mock(FilesystemMetrics.class);

    @Rule
    public ResourceTestRule resources = ResourceTestRule.builder()
            .addResource(new FilesystemResource(filesystemMock))
            .build();

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testFilesystem() throws Exception {

    }
}