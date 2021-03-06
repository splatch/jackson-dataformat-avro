package com.fasterxml.jackson.dataformat.avro.ser;

import org.apache.avro.generic.GenericArray;
import org.apache.avro.generic.GenericRecord;

import com.fasterxml.jackson.dataformat.avro.AvroGenerator;
import com.fasterxml.jackson.dataformat.avro.AvroWriteContext;

public final class ArrayWriteContext
    extends AvroWriteContext
{
    protected final GenericArray<Object> _array;
    
    public ArrayWriteContext(AvroWriteContext parent, AvroGenerator generator,
            GenericArray<Object> array)
    {
        super(TYPE_ARRAY, parent, generator, array.getSchema());
        _array = array;
    }
    
    @Override
    public final AvroWriteContext createChildArrayContext()
    {
        GenericArray<Object> arr = _createArray(_schema.getElementType());
        _array.add(arr);
        return new ArrayWriteContext(this, _generator, arr);
    }
    
    @Override
    public final AvroWriteContext createChildObjectContext() {
        GenericRecord rec = _createRecord(_schema.getElementType());
        _array.add(rec);
        return new ObjectWriteContext(this, _generator, rec);
    }
    
    @Override
    public void writeValue(Object value) {
        _array.add(value);
    }
    
    @Override
    public void appendDesc(StringBuilder sb)
    {
        sb.append('[');
        sb.append(getCurrentIndex());
        sb.append(']');
    }
}