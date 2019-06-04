package serverclient.server.repository;

import serverclient.model.MessageOld;

public interface MessageRepository {

    MessageOld create(MessageOld message);

    MessageOld delete(MessageOld message);

    boolean existId(long id);

    MessageOld read(MessageOld message);

    MessageOld update(MessageOld message);

}
