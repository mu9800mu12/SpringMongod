package kopo.poly.service;

import java.util.List;
import kopo.poly.dto.MovieDTO;

public interface IMovieService {

    List<MovieDTO> getMovieRank() throws Exception;

}
