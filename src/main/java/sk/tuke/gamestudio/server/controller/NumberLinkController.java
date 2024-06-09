package sk.tuke.gamestudio.server.controller;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sk.tuke.gamestudio.dto.NumberLinkFieldDTO;
import sk.tuke.gamestudio.game.numberlink.core.Direction;
import sk.tuke.gamestudio.game.numberlink.core.Field;
import sk.tuke.gamestudio.game.numberlink.core.FieldGenerator;
import sk.tuke.gamestudio.game.numberlink.entity.Gameplay;
import sk.tuke.gamestudio.game.numberlink.entity.Move;
import sk.tuke.gamestudio.game.numberlink.service.GameplayService;
import sk.tuke.gamestudio.game.numberlink.service.MoveService;

import java.util.Date;

@CrossOrigin
@RestController
@RequestMapping("/api/numberlink")
public class NumberLinkController {
    private final FieldGenerator fieldGenerator = new FieldGenerator();
    private Field field;
    private Gameplay gameplay;

    @Autowired
    private GameplayService gameplayService;

    @Autowired
    private MoveService moveService;

    // POST http://localhost:8080/api/numberlink/new_game
    @PostMapping("/new_game")
    public void newGame(@RequestParam("difficulty") int difficulty) {
        field = fieldGenerator.generateField(difficulty+1);
        gameplay = new Gameplay("gameplay_test", new Date());
        gameplayService.save(gameplay);

        Move init = new Move(gameplay, new Gson().toJson(field.getLinks()), new Date());
        moveService.save(init);
    }

    // GET http://localhost:8080/api/numberlink/
    @GetMapping
    public ResponseEntity<?> getFieldData() {
        if (field == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Field not found");
        return ResponseEntity.ok(prepareFieldData());
    }

    // POST http://localhost:8080/api/numberlink/update_field
    @PostMapping("/update_field")
    public void updateFieldData(
            @RequestParam("row") int row,
            @RequestParam("column") int column,
            @RequestParam("direction") String direction) {
        field.linkTile(row, column, parseDirection(direction));
    }

    // POST http://localhost:8080/api/numberlink/save_move
    @PostMapping("/save_move")
    public void saveMove() {
        moveService.save(new Move(gameplay, new Gson().toJson(field.getLinks()), new Date()));
    }

    // POST http://localhost:8080/api/numberlink/undo
    @PostMapping("/undo")
    public void undo() {
        if (moveService.count(gameplay) > 1) {
            moveService.delete(gameplay);

            Move previousMove = moveService.getLatest(gameplay);
            field.setLinks(previousMove.getLinks());
        }
    }

    private NumberLinkFieldDTO prepareFieldData() {
        if (field == null) return null;

        NumberLinkFieldDTO response = new NumberLinkFieldDTO();

        response.setRowCount(field.getRowCount());
        response.setColumnCount(field.getColumnCount());
        response.setState(field.getState());
        response.setTiles(field.getTiles());

        return response;
    }

    private Direction parseDirection(String directionStr) {
        return switch (directionStr) {
            case "up" -> Direction.UP;
            case "down" -> Direction.DOWN;
            case "left" -> Direction.LEFT;
            case "right" -> Direction.RIGHT;
            default -> null;
        };
    }
}
