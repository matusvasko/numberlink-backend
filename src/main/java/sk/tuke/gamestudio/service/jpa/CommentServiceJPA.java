package sk.tuke.gamestudio.service.jpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sk.tuke.gamestudio.entity.Comment;
import sk.tuke.gamestudio.repository.CommentRepository;
import sk.tuke.gamestudio.service.CommentException;
import sk.tuke.gamestudio.service.CommentService;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Service
public class CommentServiceJPA implements CommentService {
    @Autowired
    private CommentRepository commentRepository;

    @Override
    public void addComment(Comment comment) throws CommentException {
        commentRepository.save(comment);
    }

    @Override
    public List<Comment> getComments(String game) throws CommentException {
        return commentRepository.findTop10ByGameOrderByCommentedOnDesc(game);
    }

    @Override
    public void reset() throws CommentException {
        commentRepository.deleteAll();
    }
}
