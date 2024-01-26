package com.pooh.discordspring.repository;

import com.pooh.discordspring.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room,Long> {
}
