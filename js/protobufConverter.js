// Utility to convert JavaScript POJOs to Protocol Buffer format
import protobuf from 'protobufjs';
import { fileURLToPath } from 'url';
import { dirname, join } from 'path';

const __filename = fileURLToPath(import.meta.url);
const __dirname = dirname(__filename);

export class ProtobufConverter {
    static root = null;
    static LargePojoType = null;
    static LargePojoListType = null;

    // Load and compile protobuf schemas
    static async initialize() {
        if (this.root) {
            return; // Already initialized
        }

        const protoPath = join(__dirname, 'proto', 'messages.proto');
        this.root = await protobuf.load(protoPath);
        this.LargePojoType = this.root.lookupType('org.example.proto.LargePojo');
        this.LargePojoListType = this.root.lookupType('org.example.proto.LargePojoList');
    }

    // Convert a single POJO to protobuf message and encode to bytes
    static convertPojoToProto(pojo) {
        const message = this.LargePojoType.create(pojo);
        return this.LargePojoType.encode(message).finish();
    }

    // Convert a list of POJOs to protobuf message and encode to bytes
    static convertListToProto(pojoList) {
        const listMessage = this.LargePojoListType.create({ items: pojoList });
        return this.LargePojoListType.encode(listMessage).finish();
    }

    // Decode bytes to a single POJO
    static decodeProtoToPojo(bytes) {
        const message = this.LargePojoType.decode(bytes);
        return this.LargePojoType.toObject(message);
    }

    // Decode bytes to a list of POJOs
    static decodeProtoToList(bytes) {
        const message = this.LargePojoListType.decode(bytes);
        return this.LargePojoListType.toObject(message);
    }

    // Convert JS object to protobuf-compatible format
    static toProtobufCompatible(obj) {
        if (obj === null || obj === undefined) {
            return undefined; // Protobuf doesn't explicitly handle null
        }

        if (Array.isArray(obj)) {
            return obj.map(item => this.toProtobufCompatible(item));
        }

        if (typeof obj === 'object') {
            const result = {};
            for (const [key, value] of Object.entries(obj)) {
                if (value !== null && value !== undefined) {
                    result[key] = this.toProtobufCompatible(value);
                }
            }
            return result;
        }

        return obj;
    }
}
