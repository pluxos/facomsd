package cliente;

public interface DAO<K,V> {
  V get(K key);
}
