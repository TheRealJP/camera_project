package be.kdg.processor.controllers.rest;

import be.kdg.processor.dto.FineDTO;
import be.kdg.processor.dto.FineDTOMapper;
import be.kdg.processor.exceptions.FineException;
import be.kdg.processor.models.fine.Fine;
import be.kdg.processor.service.fineservice.FineService;
import org.modelmapper.ModelMapper;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * beheer van de opgeslagen boetes
 * ○ lijst van boetes (filterbaar tussen 2 tijdstippen)
 * ○ goedkeuren van (een) boete(s)
 * ○ aanpassen van boetebedrag met motivering (bv. na een gerechtelijke procedure)
 */

//DTO nodig omdat de frontend niet constant moet aangepast worden
@RestController //= @Controller + @Responsebody
@RequestMapping("/api")
public class FineRestController {
    private final FineService fineService;
    private final ModelMapper modelMapper;
    private final FineDTOMapper fineDTOMapper;

    public FineRestController(FineService fineService, ModelMapper modelMapper, FineDTOMapper fineDTOMapper) {
        this.fineService = fineService;
        this.modelMapper = modelMapper;
        this.fineDTOMapper = fineDTOMapper;
    }


    @GetMapping(value = "/fines/{id}", produces = MediaType.APPLICATION_JSON_VALUE) //append aan /api
    public ResponseEntity<FineDTO> getFine(@PathVariable Long id) throws FineException {
        Fine fine = fineService.get(id);
        return new ResponseEntity<>(modelMapper.map(fine, FineDTO.class), HttpStatus.OK);
    }

    @GetMapping(value = "/fines", produces = MediaType.APPLICATION_JSON_VALUE) //alle fines
    public ResponseEntity<List<FineDTO>> getFines() throws FineException {
        List<Fine> fines = fineService.getAll();
        return new ResponseEntity<>(fineDTOMapper.toFineDTOList(fines), HttpStatus.OK);
    }

    @PostMapping(value = "/fines", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FineDTO> createGreeting(@RequestBody FineDTO fineDTO) {
        Fine fineIn = modelMapper.map(fineDTO, Fine.class);
        Fine fineOut = fineService.save(fineIn);
        return new ResponseEntity<>(modelMapper.map(fineOut, FineDTO.class), HttpStatus.CREATED);
    }

    @PutMapping(path = "/fines/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FineDTO> putApproveFine(@PathVariable Long id, @RequestParam("approved")boolean approved) throws FineException {
        Fine fine = fineService.setApproveFine(id, approved);
        Fine fineOut = fineService.save(fine);
        return new ResponseEntity<>(modelMapper.map(fineOut, FineDTO.class), HttpStatus.ACCEPTED);
    }

//    @GetMapping(path = "/fines/filtered", produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<List<FineDTO>> loadFilteredFines(
//            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
//            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime)
//            throws FineException {
//
//        List<Fine> fines = fineService.getFilteredFines(startTime, endTime);
//        return new ResponseEntity<>(fineDTOMapper.toFineDTOList(fines) , HttpStatus.OK);
//    }


}
