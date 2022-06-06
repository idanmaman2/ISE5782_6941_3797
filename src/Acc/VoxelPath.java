package Acc;

import java.util.Iterator;

import primitives.Ray;

public class VoxelPath implements Iterable<Voxel> {
    Grid grid ; 
    Ray ray ; 
    public VoxelPath(Grid grid , Ray ray){
        this.ray = ray ; 
        this.grid = grid ; 
    }
    @Override
    public Iterator<Voxel> iterator() {
        return new VoxelPathIterator(grid, ray);
    }
    
}
