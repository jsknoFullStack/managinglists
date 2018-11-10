package com.jskno.managinglistsbe.repositories;

import com.jskno.managinglistsbe.domain.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttachmentRepository extends JpaRepository<Attachment, Long> {
}
