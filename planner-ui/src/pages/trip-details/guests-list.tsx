import { CircleCheck, CircleDashed, UserCog } from "lucide-react";
import { Button } from "../../components/button";
import { useParams } from "react-router-dom";
import { useEffect, useState } from "react";
import { api } from "../../lib/axios";

interface Participant {
  id: string
  confirmed: boolean
  email: string
  name: string | null
}

export function GuestsList() {
  const { tripId } = useParams()
  const [ participants, setParticipants ] = useState<Participant[]>([])

  useEffect(() => {    
    api.get(`/trips/${tripId}/participants`).then(response => setParticipants(response.data))
  }, [tripId])

  return (
    <div className="space-y-6">
      <h2 className="font-semibold text-xl">Convidados</h2>
      
      <div className="space-y-5">
        {participants.map(participant => {
          return (
            <div key={participant.id} className="flex items-center justify-between gap-4">
              <div className="space-y-1.5">
                <span className="block font-medium text-zinc-100">
                  {participant.name}
                </span>
                <span className="block text-sm text-zinc-400 truncate">
                  {participant.email}
                </span>
              </div>
              {participant.confirmed ? (
                <CircleCheck className="text-lime-300 size-5 shrink-0"/>
              ) : (
                <CircleDashed className="text-zinc-400 size-5 shrink-0"/>
              )}
            </div>
          )
        })}        
      </div>

      <Button variant="secondary" size="full">
        <UserCog className="size-5" />
        Gerenciar convidados
      </Button>
    </div>
  )
}