package com.billing.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/image")
public class ImageUploadController 
{

    @Operation(summary = "Upload image")
    @PostMapping( value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE )
    public ResponseEntity<String> uploadImage
    (
         @Parameter(description = "Select image file", required = true, content = 
            @Content(
                            mediaType = MediaType.MULTIPART_FORM_DATA_VALUE,
                            schema = @Schema(type = "string", format = "binary")
                    )
            )
            @RequestPart("image") MultipartFile image
    ) 
    {
        return ResponseEntity.ok(
                "Image received \n" +
                "Name: " + image.getOriginalFilename() + "\n" +
                "Type: " + image.getContentType() + "\n" +
                "Size: " + image.getSize() + " bytes"
        );
    }
}
