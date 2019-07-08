package server.commons.atomix;

import io.atomix.cluster.Node;
import io.atomix.cluster.discovery.BootstrapDiscoveryProvider;
import io.atomix.core.Atomix;
import io.atomix.core.AtomixBuilder;
import io.atomix.core.list.DistributedList;
import io.atomix.core.map.DistributedMap;
import io.atomix.core.profile.ConsensusProfile;
import io.atomix.core.value.DistributedValue;
import io.atomix.protocols.raft.MultiRaftProtocol;
import io.atomix.protocols.raft.ReadConsistency;
import io.atomix.protocols.raft.session.CommunicationStrategy;
import io.atomix.utils.net.Address;
import server.commons.chord.ChodNode;

import java.math.BigInteger;
import java.util.List;

public class ClusterAtomix {
    private static Atomix cluster;
    private static DistributedValue<Integer> key;
    private static DistributedList<Integer> range;
    private static DistributedMap<BigInteger, byte[]> db;
    private static DistributedMap<Integer, ChodNode> ft;

    public static Atomix setCluster(List<Address> addresses, int id) {
        cluster = initCluster(addresses, id);

        return cluster;
    }

    public static Atomix getCluster() {
        return cluster;
    }

    public static DistributedValue<Integer> getKey() {
        return key;
    }

    public static DistributedList<Integer> getRange() {
        return range;
    }

    public static DistributedMap<BigInteger, byte[]> getDb() {
        return db;
    }

    public static DistributedMap<Integer, ChodNode> getFt() {
        return ft;
    }

    public static void initVars() {
        range = cluster.<Integer>listBuilder("chordRange")
                .withProtocol(
                        MultiRaftProtocol.builder()
                                .withReadConsistency(ReadConsistency.LINEARIZABLE)
                                .build()
                )
                .build();

        key = cluster.<Integer>valueBuilder("ChordKey")
                .withReadOnly(true)
                .withProtocol(MultiRaftProtocol.builder()
                        .withReadConsistency(ReadConsistency.LINEARIZABLE)
                        .build())
                .build();

        db = cluster.<BigInteger, byte[]>mapBuilder("dataBase")
                .withCacheEnabled()
                .withProtocol(MultiRaftProtocol.builder()
                        .withReadConsistency(ReadConsistency.LINEARIZABLE)
                        .withCommunicationStrategy(CommunicationStrategy.LEADER)
                        .build())
                .build();

        ft = cluster.<Integer, ChodNode>mapBuilder("finger-table")
                .withProtocol(MultiRaftProtocol.builder()
                        .withReadConsistency(ReadConsistency.LINEARIZABLE)
                        .build())
                .build();
    }

    public static void clearVars() {
        range.clear();
        db.clear();
        ft.clear();
    }

    private static Atomix initCluster(List<Address> addresses, int id) {
        AtomixBuilder builder = Atomix.builder();

        return builder.withMemberId("member-" + id)
                .withAddress(addresses.get(id))
                .withMembershipProvider(BootstrapDiscoveryProvider.builder()
                        .withNodes( Node.builder()
                                        .withId("member-0")
                                        .withAddress(addresses.get(0))
                                        .build(),
                                Node.builder()
                                        .withId("member-1")
                                        .withAddress(addresses.get(1))
                                        .build(),
                                Node.builder()
                                        .withId("member-2")
                                        .withAddress(addresses.get(2))
                                        .build())
                        .build())
                .withMulticastEnabled()
                .withProfiles(
                        ConsensusProfile.builder()
                                .withDataPath("/tmp/member-" + id)
                                .withMembers("member-1", "member-2", "member-3")
                                .build())
                .build();
    }
}
