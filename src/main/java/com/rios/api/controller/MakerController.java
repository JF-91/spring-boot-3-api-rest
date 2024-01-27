package com.rios.api.controller;

import com.rios.api.controller.dto.MakerDTO;
import com.rios.api.entities.Maker;
import com.rios.api.services.IMakerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/makers")
public class MakerController {

    @Autowired
    private IMakerService makerService;

    @GetMapping("/find/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id){
        Optional<Maker> makerOptional = makerService.findById(id);

        if (makerOptional.isPresent()){
            Maker maker = makerOptional.get();
            MakerDTO makerDTO = MakerDTO.builder()
                    .id(maker.getId())
                    .name(maker.getName())
                    .build();

            return ResponseEntity.ok(makerDTO);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/find/all")
    public ResponseEntity<?> findAll(){
        Iterable<MakerDTO> makerDTOIterable = makerService.findAll()
                .stream()
                .map( maker -> MakerDTO.builder()
                        .id(maker.getId())
                        .name(maker.getName())
                        .build())
                .toList();

        return ResponseEntity.ok(makerDTOIterable);
    }

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody MakerDTO makerDTO) throws URISyntaxException {
        if(makerDTO.getName().isEmpty()){
            return ResponseEntity.badRequest().build();
        }

        Maker maker = Maker.builder()
                .id(makerDTO.getId())
                .name(makerDTO.getName())
                .build();

        makerService.save(maker);

        return ResponseEntity.created(new URI("/api/maker/save")).build();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody MakerDTO makerDTO){
        Optional<Maker> makerOptional = makerService.findById(id);

        if(makerOptional.isPresent()){
            Maker maker = makerOptional.get();
            maker.setName(makerDTO.getName());

            makerService.save(maker);

            return ResponseEntity.ok().build();
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id){
        if (makerService.findById(id).isPresent()){
            makerService.deleteById(id);
            return ResponseEntity.ok().build();
        }
        else{
            return ResponseEntity.notFound().build();
        }
    }
}
