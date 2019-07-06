package state_machine.command;

import state_machine.type.Edge;
import io.atomix.copycat.Query;

public class GetEdgeQuery implements Query<Edge>
{
    public int id;
    public int id2;

    public GetEdgeQuery(int id, int id2)
    {
        this.id = id;
        this.id2 = id2;
    }
}
