package com.dss.data.jpa.app.service.impl;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import com.dss.data.jpa.app.controller.CustomerController;
import com.dss.data.jpa.app.service.UploadFileService;

@Service
public class UploadFileServiceImpl implements UploadFileService {

	private static final Logger LOGGER = LoggerFactory.getLogger(CustomerController.class);
	private static final String UPLOAD_FOLDER = "uploads";

	@Override
	public Resource load(String filename) throws MalformedURLException {
		Path path = getPath(filename);
		LOGGER.info("Path Photo: {}", path.toString());

		Resource resource = new UrlResource(path.toUri());

		if (!resource.exists() || !resource.isReadable()) {
			throw new RuntimeException("It isn't possible load the image: " + path.toString());
		}

		return resource;

	}

	@Override
	public String copy(MultipartFile file) throws IOException {
		String filename = UUID.randomUUID().toString().concat("_").concat(file.getOriginalFilename());
		Path rootPath = getPath(filename);

		LOGGER.info("rootPath: {}", rootPath);

		Files.copy(file.getInputStream(), rootPath);

		return filename;
	}

	@Override
	public boolean delete(String filename) {
		Path path = getPath(filename);
		File file = path.toFile();

		if (file.exists() && file.canWrite()) {
			file.delete();
			return true;
		}
		return false;
	}

	private Path getPath(String filename) {
		return Paths.get(UPLOAD_FOLDER).resolve(filename).toAbsolutePath();
	}

	@Override
	public void deleteAll() {
		FileSystemUtils.deleteRecursively(Paths.get(UPLOAD_FOLDER).toFile());
	}

	@Override
	public void init() throws IOException {
		Files.createDirectory(Paths.get(UPLOAD_FOLDER));
	}

}
