using System;
using System.Collections.Generic;

namespace client
{
    public class Client
    {
        public void listenClient()
        {
            Console.WriteLine("Welcome to the SKYNET, type exit and prepare yourself for the judgement day:");
            Console.WriteLine("Create <value>");
            Console.WriteLine("Read <value>");
            Console.WriteLine("Update <value>");
            Console.WriteLine("Delete <value>");
            string aws = Console.ReadLine();
            isValidAnswer(aws);
        }

        void isValidAnswer(string aws)
        {
            Dictionary<BigInteger,Array<byte>> message = 
            ClientSocket.StartClient(aws);
            // if (String.Compare(aws, "1") < 0 || String.Compare(aws, "4") > 0)
            // {
            //     Console.WriteLine("Invalid command human douchebag!");
            // }
            // else if (String.Compare(aws, "exit") == 0)
            // {
            //     return;
            // }
            // else
            // {
            //     ClientSocket.StartClient(aws);
            // }
        }
    }
}
