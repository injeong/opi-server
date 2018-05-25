package br.edu.ufcg.dsc.opi.rest;

import java.net.URI;
import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.edu.ufcg.dsc.opi.dto.SchoolDTO;
import br.edu.ufcg.dsc.opi.models.School;
import br.edu.ufcg.dsc.opi.service.SchoolService;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = RestConstants.SCHOOL_URI)
public class SchoolRest {

	@Autowired
	private SchoolService schoolService;

	/**
	 * Endpoint to create a School.
	 * 
	 * @param school to be register
	 * @return status
	 */
	@PostMapping({ "/", "" })
	@ApiOperation(
			value = "Create a School", 
			notes = "Also returns a link to retrieve the saved school in the location header"
	)
	public ResponseEntity<Object> createSchool(@Valid @RequestBody SchoolDTO school) {
		School savedSchool = schoolService.create(school.toModel());

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(savedSchool.getId()).toUri();

		return ResponseEntity.created(location).build();
	}

	@GetMapping({ "/", "" })
	public Collection<SchoolDTO> indexSchool() {
		return schoolService.index();
	}

}