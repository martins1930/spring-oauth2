package org.example.albums.controller;

import java.util.List;
import org.example.albums.domain.Album;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/albums")
public class AlbumController {

  @GetMapping
  public List<Album> getAlbums() {
    Album album1 = new Album();
    album1.setAlbumId("album1");
    album1.setAlbumTitle("Album one");
    album1.setAlbumUrl("http://localhost:8082/albums/1");

    Album album2 = new Album();
    album2.setAlbumId("album2");
    album2.setAlbumTitle("Album two");
    album2.setAlbumUrl("http://localhost:8082/albums/2");

    Album album3 = new Album();
    album3.setAlbumId("album3");
    album3.setAlbumTitle("Album three from the album service!");
    album3.setAlbumUrl("http://localhost:8082/albums/3");

    return List.of(album1, album2, album3);
  }
}
