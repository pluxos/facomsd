package state_machine.command;

import state_machine.type.Vertex;
import io.atomix.copycat.Query;

public class GetVertexQuery implements Query<Vertex>
{
    public int id;

    public GetVertexQuery(int id)
    {
        this.id = id;
    }
}
