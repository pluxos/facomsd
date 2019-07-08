package br.ufu.ds.server;

import io.atomix.cluster.Node;
import io.atomix.cluster.discovery.BootstrapDiscoveryProvider;
import io.atomix.core.Atomix;
import io.atomix.core.map.DistributedMap;
import io.atomix.core.profile.ConsensusProfile;
import io.atomix.primitive.partition.MemberGroupStrategy;
import io.atomix.protocols.backup.partition.PrimaryBackupPartitionGroup;
import io.atomix.protocols.raft.partition.RaftPartitionGroup;

import java.io.File;
import java.math.BigInteger;
import java.time.Duration;

public class Server {

    public static void main(String[] args) {

        String id = args[0];
        String address = args[1];

        Atomix atomix = Atomix.builder()
                .withMemberId("member-" + id)
                .withAddress(address)
                .withMulticastEnabled()
                .withMembershipProvider(BootstrapDiscoveryProvider.builder()
                        .withNodes(
                                Node.builder()
                                        .withId("member-1")
                                        .withAddress("localhost:8001")
                                        .build(),
                                Node.builder()
                                        .withId("member-2")
                                        .withAddress("localhost:8002")
                                        .build(),
                                Node.builder()
                                        .withId("member-3")
                                        .withAddress("localhost:8003")
                                .build())
                        .withHeartbeatInterval(Duration.ofSeconds(1))
                        .build())
                .withProfiles(ConsensusProfile.builder()
                        .withDataPath("/tmp/data/" + id)
                        .withMembers("member-1", "member-2", "member-3")
                        .build())
                .build();

        atomix.start().join();

        System.out.println("Cluster joined: " + id);
    }

}
