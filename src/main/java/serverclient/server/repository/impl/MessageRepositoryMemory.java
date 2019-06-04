package serverclient.server.repository.impl;

import serverclient.model.MessageOld;
import serverclient.server.repository.MessageRepository;
import serverclient.server.database.MemoryDB;
import serverclient.constants.StringsConstants;

import java.math.BigInteger;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

public class MessageRepositoryMemory implements MessageRepository {

    private final static Logger LOGGER = Logger.getLogger(MessageRepositoryMemory.class.getName());

    private static volatile AtomicLong counterCreator = new AtomicLong(0);

    public static long getLastId() {
        return counterCreator.get();
    }

    public static synchronized void resetAtomicLongIdCreator() {
        counterCreator = new AtomicLong(0);
    }

    public static BigInteger getKey(Map<BigInteger, String> map, String value) {
        for (BigInteger key : map.keySet()) {
            if (value.equals(map.get(key))) {
                return key;
            }
        }
        return BigInteger.valueOf(-1);
    }

    @Override
    public MessageOld create(MessageOld message) {

        LOGGER.info("Mensagem " + message.getMessage() + " será adicionada ao banco.");

        MessageOld answer = null;

        if (MemoryDB.getDatabase().containsValue(message.getMessage())) {
            long id = getKey(MemoryDB.getDatabase(), message.getMessage()).longValue();
            answer = new MessageOld(1, id, StringsConstants.ERR_EXISTENT_MESSAGE.toString());
        } else {

            long newId = counterCreator.incrementAndGet();

            while(this.existId(newId)) {
                newId = counterCreator.incrementAndGet();
            }

            MemoryDB.getDatabase().put(BigInteger.valueOf(newId), message.getMessage());
            answer = new MessageOld(1, newId, StringsConstants.MESSAGE_CREATION_SUCCESS_ID.toString() + newId + " -- " + message.getMessage());
        }

        LOGGER.info("Resposta " + answer + " será será retornada ao cliente.");
        return answer;
    }

    @Override
    public MessageOld read(MessageOld message) {

        String messageString = MemoryDB.getDatabase().get(BigInteger.valueOf(message.getId()));

        MessageOld answer = null;

        if (messageString == null) {
            answer = new MessageOld(2, StringsConstants.ERR_NON_EXISTENT_ID.toString());
        } else {
            answer = new MessageOld(2, message.getId(), messageString);
        }

        LOGGER.info("Resposta " + answer + " será será retornada ao cliente.");
        return answer;
    }

    @Override
    public MessageOld update(MessageOld message) {

        MessageOld answer = null;
        String messageFromDB = MemoryDB.getDatabase().get(BigInteger.valueOf(message.getId()));

        if (messageFromDB == null) {

            answer = new MessageOld(3, message.getId(), StringsConstants.ERR_NON_EXISTENT_ID.toString());

        } else if (MemoryDB.getDatabase().containsValue(message.getMessage())) {

            long id = getKey(MemoryDB.getDatabase(), MemoryDB.getDatabase().get(message.getMessage())).longValue();
            answer = new MessageOld(3, id, StringsConstants.ERR_EXISTENT_MESSAGE.toString());

        } else {

            MemoryDB.getDatabase().replace(BigInteger.valueOf(message.getId()), message.getMessage());
            answer = new MessageOld(3, message.getId(), StringsConstants.MESSAGE_UPDATE_SUCCESS.toString());

        }

        LOGGER.info("Resposta " + answer + " será será retornada ao cliente.");

        return answer;
    }

    @Override
    public MessageOld delete(MessageOld message) {

        MessageOld answer = null;

        String text = MemoryDB.getDatabase().remove(BigInteger.valueOf(message.getId()));

        if (text == null) {
            answer = new MessageOld(4, StringsConstants.ERR_NON_EXISTENT_ID.toString());
        } else {
            answer = new MessageOld(4, message.getId(), StringsConstants.MESSAGE_DELETE_SUCCESS.toString());
        }

        LOGGER.info("Resposta " + answer + " será será retornada ao cliente.");

        return answer;
    }

    @Override
    public boolean existId(long id) {
        return MemoryDB.getDatabase().containsKey(BigInteger.valueOf(id));
    }

}
